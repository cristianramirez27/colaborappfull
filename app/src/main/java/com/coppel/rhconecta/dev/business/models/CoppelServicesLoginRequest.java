package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesLoginRequest {

    private String email;
    private String password;
    private String app;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
