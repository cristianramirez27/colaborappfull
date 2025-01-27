package com.coppel.rhconecta.dev.domain.home.entity;

/**
 *
 *
 */
public class Badge {

    /* */
    private int value;
    /* */
    private Type type;

    /**
     *
     *
     */
    public Badge(int value, Type type) {
        this.value = value;
        this.type = type;
    }

    /**
     *
     *
     */
    public int getValue() {
        return value;
    }

    /**
     *
     *
     */
    public Type getType() {
        return type;
    }

    /**
     *
     *
     */
    public enum Type {

        /* */
        RELEASE,
        /* */
        VISIONARY,
        /* */
        COLLABORATOR_AT_HOME,
        /* */
        POLL

    }

}
