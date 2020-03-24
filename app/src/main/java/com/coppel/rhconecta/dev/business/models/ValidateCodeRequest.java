package com.coppel.rhconecta.dev.business.models;

public class ValidateCodeRequest {
    private int opcion;
    private String qrcode;
    private int usuario;
    private String emailemp;
    private String deviceid;

    public ValidateCodeRequest(){}

    public ValidateCodeRequest(int opcion, String qrcode, int usuario, String emailemp, String deviceid){
        this.opcion = opcion;
        this.qrcode = qrcode;
        this.usuario = usuario;
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

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
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
