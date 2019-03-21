package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class DatosAbonoOpcion implements Serializable {

    private int importe;
    private String des_proceso;
    private String des_cambiar;

    public DatosAbonoOpcion(int importe, String des_proceso, String des_cambiar) {
        this.importe = importe;
        this.des_proceso = des_proceso;
        this.des_cambiar = des_cambiar;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public String getDes_proceso() {
        return des_proceso;
    }

    public void setDes_proceso(String des_proceso) {
        this.des_proceso = des_proceso;
    }

    public String getDes_cambiar() {
        return des_cambiar;
    }

    public void setDes_cambiar(String des_cambiar) {
        this.des_cambiar = des_cambiar;
    }
}
