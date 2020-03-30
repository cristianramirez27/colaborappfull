package com.coppel.rhconecta.dev.domain.visionary.entity;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class VisionaryPreview {

    private String id;
    private String title;
    private String date;
    private String previewImage;
    private int numberOfViews;
    private boolean alreadyBeenSeen;

    public VisionaryPreview(String id, String title, String date, String previewImage, int numberOfViews, boolean alreadyBeenSeen) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.previewImage = previewImage;
        this.numberOfViews = numberOfViews;
        this.alreadyBeenSeen = alreadyBeenSeen;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public boolean isAlreadyBeenSeen() {
        return alreadyBeenSeen;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
