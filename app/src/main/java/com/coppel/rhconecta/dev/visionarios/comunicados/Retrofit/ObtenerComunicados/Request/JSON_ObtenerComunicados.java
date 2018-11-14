package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request;

public class JSON_ObtenerComunicados {

    private String aplicacionkey;


    public JSON_ObtenerComunicados() {
        this.aplicacionkey="c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerComunicados(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}
