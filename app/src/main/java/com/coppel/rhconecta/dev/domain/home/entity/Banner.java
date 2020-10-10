package com.coppel.rhconecta.dev.domain.home.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

/**
 *
 *
 */
public class Banner {

    /* */
    private String id;
    /* */
    private String src;
    /* */
    private String image;
    /* */
    private int type;
    /* */
    private String urlLink;

    /**
     *
     *
     */
    public Banner(String id, String src, String image, int type, String urlLink) {
        this.id = id;
        this.src = src;
        this.image = image;
        this.type = type;
        this.urlLink = urlLink;
    }

    /**
     *
     */
    public String getId() {
        return id;
    }

    /**
     *
     */
    public String getSrc() {
        return src;
    }

    /**
     *
     */
    public String getImage() {
        return image;
    }

    /**
     *
     */
    public int getType() {
        return type;
    }

    /**
     *
     */
    public String getUrlLink() {
        return urlLink;
    }

    /**
     *
     */
    public boolean isRelease() {
        return type == 1;
    }

    /**
     *
     *
     */
    public boolean isVisionary(){
        return type == 2;
    }

    /**
     *
     */
    public boolean isVisionaryAtHome(){
        return type == 3;
    }

    /**
     *
     */
    public boolean isLink(){
        return type == 4;
    }

    /**
     *
     *
     */
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     *
     *
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Banner)
            return ((Banner) obj).id.equals(id);
        return false;
    }

}
