import React, { useEffect } from "react";
import { Spin } from "antd";
import { Header, MainContent, Footer } from "../";
import NewsCard from "./NewsCard";
import styles from "./News.module.css";

function News(props) {
  let { handleExpensesRequest } = props;
  useEffect(() => {
    let politicianId = new URLSearchParams(window.location.search).get(
      "politician"
    );
    handleExpensesRequest(politicianId);
  }, [handleExpensesRequest]);

  let buildNewsCards = (news) => {
    let cards = [];
    news.forEach((newsElement) => {
      cards.push(<NewsCard data={newsElement} />);
    });
    return cards;
  };

  return (
    <Spin spinning={props.loading} tip="Carregando...">
      <Header />
      <MainContent>
        <div className={styles.container}>{buildNewsCards(props.data)}</div>
      </MainContent>
      <Footer />
    </Spin>
  );
}

export default News;
