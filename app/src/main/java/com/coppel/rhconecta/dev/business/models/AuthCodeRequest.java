package com.coppel.rhconecta.dev.business.models;

public class AuthCodeRequest {
    private int opcion;
    private String qrcode;
    private int status;
    private String emailemp;
    private String deviceid;

    public AuthCodeRequest(){}

    public AuthCodeRequest(int opcion, String qrcode, int status, String emailemp, String deviceid){
        this.opcion = opcion;
        this.qrcode = qrcode;
        this.status = status;
        this.emailemp = emailemp;
        this.deviceid = deviceid;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getEmailemp() {
        return emailemp;
    }

    public void setEmailemp(String emailemp) {
        this.emailemp = emailemp;
    }
}
