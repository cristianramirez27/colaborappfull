package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction.Request;

public class JSON_GuardarLogAction {

    private String aplicacionkey;
    private int numeroempleado;
    private int idvideo;
    private int tipolog;

    public JSON_GuardarLogAction(String aplicacionkey, int numeroempleado, int idvideo, int tipolog) {
        this.aplicacionkey = aplicacionkey;
        this.numeroempleado = numeroempleado;
        this.idvideo = idvideo;
        this.tipolog = tipolog;
    }

    public JSON_GuardarLogAction() {
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public int getNumeroempleado() {
        return numeroempleado;
    }

    public void setNumeroempleado(int numeroempleado) {
        this.numeroempleado = numeroempleado;
    }

    public int getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(int idvideo) {
        this.idvideo = idvideo;
    }

    public int getTipolog() {
        return tipolog;
    }

    public void setTipolog(int tipolog) {
        this.tipolog = tipolog;
    }
}

