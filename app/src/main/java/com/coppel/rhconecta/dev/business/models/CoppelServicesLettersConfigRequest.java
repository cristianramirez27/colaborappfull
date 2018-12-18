package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesLettersConfigRequest {

    private String num_empleado;
    private int tipo_carta;

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

}
