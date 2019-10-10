package com.coppel.rhconecta.dev.business.models;

public class NotificationEvent {

    private String module;

    public NotificationEvent(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
