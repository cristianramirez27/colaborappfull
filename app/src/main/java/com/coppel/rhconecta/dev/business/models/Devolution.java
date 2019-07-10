package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Devolution implements Serializable {

    private String fec_deposito;
    private String desc_movimiento;
    private int idu_tipoGasto;
    private String importe;
    private String total;
    private String imp_total;


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
        return idu_tipoGasto;
    }

    public void setDu_tipoGasto(int du_tipoGasto) {
        this.idu_tipoGasto = du_tipoGasto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImp_total() {
        return imp_total;
    }

    public void setImp_total(String imp_total) {
        this.imp_total = imp_total;
    }
}
