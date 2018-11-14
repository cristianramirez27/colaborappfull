package com.coppel.rhconecta.dev.business.utils;

public class ServicesError {

    private String message = "";
    private int type = 0;
    private boolean executeInBackground;

    public ServicesError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExecuteInBackground() {
        return executeInBackground;
    }

    public void setExecuteInBackground(boolean executeInBackground) {
        this.executeInBackground = executeInBackground;
    }
}
