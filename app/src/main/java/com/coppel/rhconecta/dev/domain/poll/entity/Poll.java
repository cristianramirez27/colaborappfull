package com.coppel.rhconecta.dev.domain.poll.entity;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

/**
 *
 *
 */
public class Poll {

    /* */
    private int id;
    /* */
    private String title;
    /* */
    private String image;
    /* */
    private String imagePreview;
    /* */
    private List<Question> questions;

    /**
     *
     *
     */
    public Poll(int id, String title, String image, String imagePreview, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imagePreview = imagePreview;
        this.questions = questions;
    }

    /**
     *
     *
     */
    public int getId() {
        return id;
    }

    /**
     *
     *
     */
    public String getTitle() {
        return title;
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
    public String getImagePreview() {
        return imagePreview;
    }

    /**
     *
     *
     */
    public List<Question> getQuestions() {
        return questions;
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
}
