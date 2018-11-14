package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Request;

public class JSON_ObtenerEncuestasPreguntas {

    private String aplicacionkey;

    private int idencuesta;

    public JSON_ObtenerEncuestasPreguntas() {
        this.aplicacionkey="c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerEncuestasPreguntas(int idencuesta) {
        this.idencuesta = idencuesta;
        this.aplicacionkey="c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerEncuestasPreguntas(String aplicacionkey, int idencuesta) {
        this.aplicacionkey = aplicacionkey;
        this.idencuesta = idencuesta;
    }

    public int getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(int idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}
