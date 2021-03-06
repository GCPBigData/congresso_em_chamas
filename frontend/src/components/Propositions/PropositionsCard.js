import React from "react";
import { Card } from "antd";
import styles from "./PropositionsCard.module.css";
import { FilePdfOutlined } from "@ant-design/icons";
import PropositionTree from "./PropositionTree";

function PropositionsCard(props) {
  const { title, link, typeDescription } = props.proposition;

  return (
    <Card className={styles.card}>
      <h3>{title}</h3>
      <h4 className={styles.type}>{typeDescription}</h4>
      <div className={styles.details}>
        <PropositionTree proposition={props.proposition} />
        <a href={link} target="_blank" rel="noopener noreferrer">
          <FilePdfOutlined className={styles.pdf} />
        </a>
      </div>
    </Card>
  );
}

export default PropositionsCard;
