package com.coppel.rhconecta.dev.business.models;

public class QrCodeResponse {
    private int iEstado;
    private String sMensaje;

    QrCodeResponse(int iEstado, String sMensaje){
        this.iEstado = iEstado;
        this.sMensaje = sMensaje;
    }

    public int getEstado() {
        return iEstado;
    }

    public void setEstado(int iEstado) {
        this.iEstado = iEstado;
    }

    public String getMensaje() {
        return sMensaje;
    }

    public void setMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }
}