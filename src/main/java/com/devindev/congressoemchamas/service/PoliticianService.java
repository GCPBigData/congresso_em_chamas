package com.devindev.congressoemchamas.service;

import com.devindev.congressoemchamas.data.news.News;
import com.devindev.congressoemchamas.data.politician.Politician;
import com.devindev.congressoemchamas.data.politician.PoliticianRepository;
import com.devindev.congressoemchamas.data.proposition.Proposition;
import com.devindev.congressoemchamas.externalapi.camara.CamaraAPI;
import com.devindev.congressoemchamas.externalapi.google.GoogleSearchAPI;
import com.devindev.congressoemchamas.externalapi.twitter.TwitterAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoliticianService {

    @Autowired
    private PoliticianRepository politiciansRepository;

    @Autowired
    private CamaraAPI camaraAPI;

    @Autowired
    private TwitterAPI twitterAPI;

    @Autowired
    private GoogleSearchAPI googleSearchAPI;

    @Value("${congresso.news.cache-expiration-minutes}")
    private Integer cacheExpirationMinutes;

    private Long currentLegislatureId;

    public List<Long> findPoliticianIdsByName(String name){
        return camaraAPI.requestPoliticianIdsByNameAndLegislatureId(name, getCurrentLegislatureId());
    }

    public Politician findPolitician(Long politicianId){
        Politician politician = politiciansRepository.findById(politicianId);
        if(Objects.isNull(politician)){
            politician = buildNewPoliticianAndSave(politicianId);
        }
        else if(newsInvalid(politician)){
            politician = updateNewsAndSave(politician);
        }
        return politician;
    }

    public List<Proposition> findPropositionsByPoliticianId(Long politicianId){
        List<Long> propositionIds = camaraAPI.requestPropositionIdsByPoliticianId(politicianId);
        List<Proposition> propositions = new ArrayList<>();
        propositionIds.forEach(propositionId -> {
            Proposition proposition = camaraAPI.requestPropositionFromId(propositionId);
            proposition.setAuthors(camaraAPI.requestAuthorsFromPropositionId(propositionId));
            proposition.setProcessingHistory(camaraAPI.requestProcessingHistoryFromPropositionId(propositionId));
            Collections.sort(proposition.getProcessingHistory());
            propositions.add(proposition);
        });
        return propositions;
    }

    private Long getCurrentLegislatureId(){
        if(Objects.isNull(currentLegislatureId)){
            currentLegislatureId = camaraAPI.requestCurrentLegislatureId();
            return currentLegislatureId;
        }
        else{
            return currentLegislatureId;
        }
    }

    private Politician buildNewPoliticianAndSave(Long camaraPoliticianId){
        Politician politician = camaraAPI.requestPoliticianById(camaraPoliticianId);
        politician.setTwitterUsername(twitterAPI.requestTwitterUsernameByName(politician.getName()));
        politician.setNews(googleSearchAPI.searchNewsByPoliticianName(politician.getName()));
        for (News news : politician.getNews()) {
            news.setPolitician(politician);
        }
        return politiciansRepository.save(politician);
    }

    private boolean newsInvalid(Politician politician){
        List<News> politicianNews = politician.getNews();
        return Objects.isNull(politicianNews) || politicianNews.isEmpty() || politicianNews.stream().anyMatch(news -> {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, - cacheExpirationMinutes);
            return news.getRequestTimestamp().before(now.getTime());
        });
    }

    private Politician updateNewsAndSave(Politician politician){
        List<News> requestedNews = googleSearchAPI.searchNewsByPoliticianName(politician.getName());
        for (int i=0;i<politician.getNews().size();i++){
            requestedNews.get(i).setId(politician.getNews().get(i).getId());
        }
        politician.setNews(requestedNews);
        return politiciansRepository.save(politician);
    }
}
