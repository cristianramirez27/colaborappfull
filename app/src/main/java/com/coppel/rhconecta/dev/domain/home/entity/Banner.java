package com.coppel.rhconecta.dev.domain.home.entity;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class Banner {

    private String id;
    private String src;
    private String image;
    private int type;

    public Banner(String id, String src, String image, int type) {
        this.id = id;
        this.src = src;
        this.image = image;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
