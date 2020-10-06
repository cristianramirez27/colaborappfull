package com.coppel.rhconecta.dev.presentation.common.extension;

/**
 *
 */
public class StringExtension {

    /* */
    private String value;

    /**
     *
     */
    public StringExtension(String value) {
        this.value = value;
    }

    /**
     *
     */
    public boolean isNullOrEmpty(){
        return value == null || value.trim().isEmpty();
    }

    /**
     *
     */
    public boolean isNotNullOrEmpty(){
        return !isNullOrEmpty();
    }

    /**
     *
     */
    public String getValue() {
        return value;
    }
    
}
