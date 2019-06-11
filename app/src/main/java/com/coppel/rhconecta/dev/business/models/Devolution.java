package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Devolution implements Serializable {

    private String fec_deposito;
    private String desc_movimiento;
    private int du_tipoGasto;
    private double importe;
    private double total;


    public String getFec_deposito() {
        return fec_deposito;
    }

    public void setFec_deposito(String fec_deposito) {
        this.fec_deposito = fec_deposito;
    }

    public String getDesc_movimiento() {
        return desc_movimiento;
    }

    public void setDesc_movimiento(String desc_movimiento) {
        this.desc_movimiento = desc_movimiento;
    }

    public int getDu_tipoGasto() {
        return du_tipoGasto;
    }

    public void setDu_tipoGasto(int du_tipoGasto) {
        this.du_tipoGasto = du_tipoGasto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
