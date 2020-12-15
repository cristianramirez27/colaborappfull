package com.coppel.rhconecta.dev.domain.release.entity;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class ReleasePreview {

    private int id;
    private String title;
    private String date;
    private boolean wasRead;
    private boolean isUpdated;

    public ReleasePreview(int id, String title, String date, boolean wasRead, boolean isUpdated) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.wasRead = wasRead;
        this.isUpdated = isUpdated;
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

    public boolean isUpdated() {
        return isUpdated;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
