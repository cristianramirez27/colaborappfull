package com.coppel.rhconecta.dev.domain.release.entity;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class ReleasePreview {

    private int id;
    private String title;
    private String date;
    private boolean wasRead;

    public ReleasePreview(int id, String title, String date, boolean wasRead) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.wasRead = wasRead;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public boolean wasRead() {
        return wasRead;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
