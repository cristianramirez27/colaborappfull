package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Request;

public class JSON_ObtenerVideosDetalle {

    private String aplicacionkey;
    private int idvideo;
    private String user;


    public JSON_ObtenerVideosDetalle(String aplicacionkey, int idvideo, String user) {
        this.aplicacionkey = aplicacionkey;
        this.idvideo = idvideo;
        this.user = user;
    }

    public JSON_ObtenerVideosDetalle() {
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerVideosDetalle(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}

