package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ColaboratorHoliday implements Serializable {

    private String name;
    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
