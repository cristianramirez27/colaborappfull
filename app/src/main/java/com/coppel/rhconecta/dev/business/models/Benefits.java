package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Benefits implements Serializable {

    private int id;
    private String name;
    private String description;
    private String urlImage;

    public Benefits() {
    }

    public Benefits(int id, String name, String description, String urlImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
