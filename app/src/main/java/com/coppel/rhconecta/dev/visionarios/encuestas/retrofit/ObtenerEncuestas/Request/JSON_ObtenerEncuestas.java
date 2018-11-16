package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Request;

public class JSON_ObtenerEncuestas {

    private String aplicacionkey;
    private String empleado;

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

    public JSON_ObtenerEncuestas(String aplicacionkey, String empleado) {
        this.aplicacionkey = aplicacionkey;
        this.empleado = empleado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
}
