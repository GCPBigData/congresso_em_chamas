package com.devindev.congressoemchamas.politician;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

// TODO: 18-Mar-20 Added serializable and getter+setter because of serialization exception. consider removing one of those.
@Getter @Setter
public class News implements Serializable {

    private String title;

    private String link;

    private String description;

    public News(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }
}