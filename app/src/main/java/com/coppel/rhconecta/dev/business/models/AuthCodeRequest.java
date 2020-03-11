package com.coppel.rhconecta.dev.business.models;

public class AuthCodeRequest {
    private String cadena;
    private int status;
    private int opcion;

    public AuthCodeRequest(String cadena, int status, int opcion){
        this.cadena = cadena;
        this.status = status;
        this.opcion = opcion;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
