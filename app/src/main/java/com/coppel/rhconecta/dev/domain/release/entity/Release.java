package com.coppel.rhconecta.dev.domain.release.entity;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class Release {

    private int id;
    private String header;
    private String headerImage;
    private String title;
    private String content;
    private String image;

    public Release(int id, String header, String headerImage, String title, String content, String image) {
        this.id = id;
        this.header = header;
        this.headerImage = headerImage;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
