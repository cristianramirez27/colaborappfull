package com.coppel.rhconecta.dev.domain.visionary.entity;

/**
 *
 *
 */
public class Visionary {

    /* */
    private String id;
    /* */
    private String title;
    /* */
    private String date;
    /* */
    private String content;
    /* */
    private String video;
    /* */
    private int numberOfViews;
    /* */
    private boolean alreadyBeenSeen;
    /* */
    private Status status;

    /**
     *
     *
     */
    public Visionary(String id, String title, String date, String content, String video, int numberOfViews, boolean alreadyBeenSeen, Status status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.video = video;
        this.numberOfViews = numberOfViews;
        this.alreadyBeenSeen = alreadyBeenSeen;
        this.status = status;
    }

    /**
     *
     *
     */
    public Visionary cloneVisionary(){
        return new Visionary(
                id,
                title,
                date,
                content,
                video,
                numberOfViews,
                alreadyBeenSeen,
                status
        );
    }

    /**
     *
     *
     */
    public enum Status {
        /* */
        UNKNOWN,
        /* */
        EMPTY,
        /* */
        LIKED,
        /* */
        DISLIKED
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
    public String getTitle() {
        return title;
    }

    /**
     *
     *
     */
    public String getDate() {
        return date;
    }

    /**
     *
     *
     */
    public String getContent() {
        return content;
    }

    /**
     *
     *
     */
    public String getVideo() {
        return video;
    }

    /**
     *
     *
     */
    public int getNumberOfViews() {
        return numberOfViews;
    }

    /**
     *
     *
     */
    public boolean isAlreadyBeenSeen() {
        return alreadyBeenSeen;
    }

    /**
     *
     *
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     *
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
