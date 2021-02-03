package com.coppel.rhconecta.dev.domain.visionary.entity;

import androidx.annotation.Keep;

import java.io.Serializable;

/* */
@Keep
public class Visionary implements Serializable {

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
    private RateStatus rateStatus;
    /* */
    private VisionaryRate visionaryRate;

    /**
     *
     */
    public Visionary(
            String id,
            String title,
            String date,
            String content,
            String video,
            int numberOfViews,
            boolean alreadyBeenSeen,
            RateStatus rateStatus,
            VisionaryRate visionaryRate
    ) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.video = video;
        this.numberOfViews = numberOfViews;
        this.alreadyBeenSeen = alreadyBeenSeen;
        this.rateStatus = rateStatus;
        this.visionaryRate = visionaryRate;
    }

    /**
     *
     */
    public Visionary cloneVisionary() {
        return new Visionary(
                id,
                title,
                date,
                content,
                video,
                numberOfViews,
                alreadyBeenSeen,
                rateStatus,
                visionaryRate
        );
    }

    /**
     *
     */
    @Keep
    public enum RateStatus implements Serializable {

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
     */
    public String getId() {
        return id;
    }

    /**
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     */
    public String getDate() {
        return date;
    }

    /**
     *
     */
    public String getContent() {
        return content;
    }

    /**
     *
     */
    public String getVideo() {
        return video;
    }

    /**
     *
     */
    public int getNumberOfViews() {
        return numberOfViews;
    }

    /**
     *
     */
    public boolean isAlreadyBeenSeen() {
        return alreadyBeenSeen;
    }

    /**
     *
     */
    public RateStatus getRateStatus() {
        return rateStatus;
    }

    /**
     *
     */
    public void setRateStatus(RateStatus rateStatus) {
        this.rateStatus = rateStatus;
    }

    /**
     *
     */
    public VisionaryRate getVisionaryRate() {
        return visionaryRate;
    }

}
