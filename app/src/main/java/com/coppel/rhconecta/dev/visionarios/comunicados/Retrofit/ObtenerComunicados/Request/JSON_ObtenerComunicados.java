package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request;

public class JSON_ObtenerComunicados {

    private String aplicacionkey;
    private String empleado;
    private int idaviso;

    public JSON_ObtenerComunicados() {
        this.aplicacionkey="c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_ObtenerComunicados(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public JSON_ObtenerComunicados(String aplicacionkey, String empleado) {
        this.aplicacionkey = aplicacionkey;
        this.empleado = empleado;
    }

    public JSON_ObtenerComunicados(String aplicacionkey, String empleado, int idaviso) {
        this.aplicacionkey = aplicacionkey;
        this.empleado = empleado;
        this.idaviso = idaviso;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getempleado() { return empleado; }

    public void setempleado(String empleado) { this.empleado = empleado; }

    public int getIdaviso() { return idaviso; }

    public void setIdaviso(int idaviso) { this.idaviso = idaviso; }
}
