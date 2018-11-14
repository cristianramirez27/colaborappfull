package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request;


public class JSON_ObtenerVideos {

    private String aplicacionkey;

    public JSON_ObtenerVideos() {
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerVideos(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}
