package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesLoginRequest {

    private String email;
    private String password;
    private String app;
    private String version;
    private Integer so_dispositivo = 1;//se establece 1 para Android

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getSo_dispositivo() {
        return so_dispositivo;
    }

    public void setSo_dispositivo(Integer so_dispositivo) {
        this.so_dispositivo = so_dispositivo;
    }
}
