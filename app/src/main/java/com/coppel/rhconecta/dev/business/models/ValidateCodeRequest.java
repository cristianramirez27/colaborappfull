package com.coppel.rhconecta.dev.business.models;

public class ValidateCodeRequest {
    private String cadena;
    private int cliente;
    private int opcion;

    public ValidateCodeRequest(String cadena, int cliente, int opcion){
        this.cadena = cadena;
        this.cliente = cliente;
        this.opcion = opcion;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
