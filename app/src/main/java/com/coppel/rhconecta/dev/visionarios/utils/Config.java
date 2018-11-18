package com.coppel.rhconecta.dev.visionarios.utils;

public class Config {
    private int id;
    private String URL_VISIONARIOS;

    public Config(String URL_VISIONARIOS) {
        this.URL_VISIONARIOS = URL_VISIONARIOS;
    }

    public String getURL_VISIONARIOS() {
        return URL_VISIONARIOS;
    }

    public void setURL_VISIONARIOS(String URL_VISIONARIOS) {
        this.URL_VISIONARIOS = URL_VISIONARIOS;
    }

    public Config(int id, String URL_VISIONARIOS) {
        this.id = id;
        this.URL_VISIONARIOS = URL_VISIONARIOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
