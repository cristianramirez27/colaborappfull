package com.coppel.rhconecta.dev.presentation.common.extension;

public class StringExtension {

    private String value;

    public StringExtension(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public boolean isNullOrEmpty(){
        return value == null || value.trim().isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean isNotNullOrEmpty(){
        return !isNullOrEmpty();
    }

    public String getValue() {
        return value;
    }
}
