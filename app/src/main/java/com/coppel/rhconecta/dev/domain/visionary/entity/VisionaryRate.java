package com.coppel.rhconecta.dev.domain.visionary.entity;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/* */
@Keep
public class VisionaryRate implements Serializable {

    /* */
    private final String titleWhenLike;

    /* */
    private final List<Option> optionsWhenLike;

    /* */
    private final String titleWhenDislike;

    /* */
    private final List<Option> optionsWhenDislike;

    /**
     *
     */
    public VisionaryRate(String titleWhenLike, List<Option> optionsWhenLike, String titleWhenDislike, List<Option> optionsWhenDislike) {
        this.titleWhenLike = titleWhenLike;
        this.optionsWhenLike = optionsWhenLike;
        this.titleWhenDislike = titleWhenDislike;
        this.optionsWhenDislike = optionsWhenDislike;
    }

    /**
     *
     */
    public String getTitleWhenLike() {
        return titleWhenLike;
    }

    /**
     *
     */
    public List<Option> getOptionsWhenLike() {
        return optionsWhenLike;
    }

    /**
     *
     */
    public String getTitleWhenDislike() {
        return titleWhenDislike;
    }

    /**
     *
     */
    public List<Option> getOptionsWhenDislike() {
        return optionsWhenDislike;
    }

    /**
     *
     */
    @Keep
    public enum Type implements Serializable {

        EMPTY,

        LIKED,

        DISLIKED;

    }

    /**
     *
     */
    @Keep
    public static class Option implements Serializable {

        /* */
        private final String id;

        /* */
        private final String content;

        /* */
        public boolean isSelected;

        /**
         *
         */
        public Option(String id, String content) {
            this.id = id;
            this.content = content;
            isSelected = false;
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
        public String getContent() {
            return content;
        }

        /**
         *
         */
        public boolean isSelected() {
            return isSelected;
        }

    }

}
