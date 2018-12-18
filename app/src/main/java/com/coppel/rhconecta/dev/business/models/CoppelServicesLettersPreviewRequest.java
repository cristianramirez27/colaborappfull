package com.coppel.rhconecta.dev.business.models;

import java.util.Map;

public class CoppelServicesLettersPreviewRequest {

    private String num_empleado;
    private int tipo_carta;
    private Map<String,Object> datos;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getTipo_carta() {
        return tipo_carta;
    }

    public void setTipo_carta(int tipo_carta) {
        this.tipo_carta = tipo_carta;
    }

    public Map<String, Object> getDatos() {
        return datos;
    }

    public void setDatos(Map<String, Object> datos) {
        this.datos = datos;
    }
}
