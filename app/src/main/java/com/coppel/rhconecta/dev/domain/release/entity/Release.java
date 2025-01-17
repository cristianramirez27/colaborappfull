package com.coppel.rhconecta.dev.domain.release.entity;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Release {

    private String id;
    private String header;
    private String headerImage;
    private String title;
    private String content;
    private String image;
    private String url_pdf;

    public Release(String id, String header, String headerImage, String title, String content, String image,String url_pdf) {
        this.id = id;
        this.header = header;
        this.headerImage = headerImage;
        this.title = title;
        this.content = content;
        this.image = image;
        this.url_pdf = url_pdf;
    }

    public String getId() {
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

    public String getUrl_pdf() {
        return url_pdf;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
