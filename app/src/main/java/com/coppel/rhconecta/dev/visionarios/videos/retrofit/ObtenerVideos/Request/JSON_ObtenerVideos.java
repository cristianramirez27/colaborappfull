package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request;


public class JSON_ObtenerVideos {

    private String aplicacionkey;
    private String empleado;
    private int idvideo;

    private int vistas;

    public JSON_ObtenerVideos() {
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerVideos(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public JSON_ObtenerVideos(String aplicacionkey, String empleado) {
        this.aplicacionkey = aplicacionkey;
        this.empleado = empleado;
    }

    public JSON_ObtenerVideos(String aplicacionkey, String empleado, int idvideo, int vistas) {
        this.aplicacionkey = aplicacionkey;
        this.empleado = empleado;
        this.idvideo = idvideo;
        this.vistas = vistas;

    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public int getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(int idvideo) {
        this.idvideo = idvideo;
    }

    public int getVistas() {
        return vistas;
    }

    public void setVistas(int vistas) {
        this.vistas = vistas;
    }
}

