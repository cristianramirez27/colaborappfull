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

    /**
     *
     *
     */
    public Banner(String id, String src, String image, int type) {
        this.id = id;
        this.src = src;
        this.image = image;
        this.type = type;
    }

    /**
     *
     *
     */
    public String getId() {
        return id;
    }

    /**
     *
     *
     */
    public String getSrc() {
        return src;
    }

    /**
     *
     *
     */
    public String getImage() {
        return image;
    }

    /**
     *
     *
     */
    public int getType() {
        return type;
    }

    /**
     *
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
     *
     */
    public boolean isVisionaryAtHome(){
        return type == 3;
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
