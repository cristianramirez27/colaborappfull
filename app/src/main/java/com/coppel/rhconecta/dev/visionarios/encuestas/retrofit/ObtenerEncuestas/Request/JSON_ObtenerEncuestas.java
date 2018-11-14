package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Request;

public class JSON_ObtenerEncuestas {

    private String aplicacionkey;

    public JSON_ObtenerEncuestas() {
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerEncuestas(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}
