package com.devindev.congressoemchamas.batch;

import com.devindev.congressoemchamas.batch.reader.CamaraReader;
import com.devindev.congressoemchamas.batch.writer.CamaraWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataUpdaterStepsManager {

    @Autowired
    private StepBuilderFactory factory;

    @Autowired
    private CamaraReader camaraReader;

    @Autowired
    private CamaraWriter camaraWriter;

    @Bean
    public Step loadDatabasePolitician() {
        return factory.get("someStep")
                .<Long, Long>chunk(10)
                .reader(camaraReader)
                .writer(camaraWriter)
                .build();
    }

    @Bean
    public Step loadCamaraPolitician() {
        return factory.get("someStep")
                .<Long, Long>chunk(10)
                .reader(camaraReader)
                .writer(camaraWriter)
                .build();
    }

    @Bean
    public Step loadCamaraExpenses() {
        return factory.get("someStep")
                .<Long, Long>chunk(10)
                .reader(camaraReader)
                .writer(camaraWriter)
                .build();
    }

    @Bean
    public Step loadCamaraPropositions() {
        return factory.get("someStep")
                .<Long, Long>chunk(10)
                .reader(camaraReader)
                .writer(camaraWriter)
                .build();
    }

    @Bean
    public Step savePolitician() {
        return factory.get("someStep")
                .<Long, Long>chunk(10)
                .reader(camaraReader)
                .writer(camaraWriter)
                .build();
    }
}
