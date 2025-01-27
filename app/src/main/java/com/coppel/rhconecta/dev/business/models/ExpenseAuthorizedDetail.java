package com.coppel.rhconecta.dev.business.models;

public class ExpenseAuthorizedDetail {

    private int idu_tipoGasto;
    private String des_tipoGasto;
    private double imp_totalAutorizado;
    private double imp_totalComprobado;
    private double imp_totalFaltante;

    public ExpenseAuthorizedDetail(int idu_tipoGasto, String des_tipoGasto, double imp_totalAutorizado) {
        this.idu_tipoGasto = idu_tipoGasto;
        this.des_tipoGasto = des_tipoGasto;
        this.imp_totalAutorizado = imp_totalAutorizado;
    }

    public int getIdu_tipoGasto() {
        return idu_tipoGasto;
    }

    public void setIdu_tipoGasto(int idu_tipoGasto) {
        this.idu_tipoGasto = idu_tipoGasto;
    }

    public String getDes_tipoGasto() {
        return des_tipoGasto;
    }

    public void setDes_tipoGasto(String des_tipoGasto) {
        this.des_tipoGasto = des_tipoGasto;
    }

    public double getImp_totalAutorizado() {
        return imp_totalAutorizado;
    }

    public void setImp_totalAutorizado(double imp_totalAutorizado) {
        this.imp_totalAutorizado = imp_totalAutorizado;
    }

    public double getImp_totalComprobado() {
        return imp_totalComprobado;
    }

    public void setImp_totalComprobado(double imp_totalComprobado) {
        this.imp_totalComprobado = imp_totalComprobado;
    }

    public double getImp_totalFaltante() {
        return imp_totalFaltante;
    }

    public void setImp_totalFaltante(double imp_totalFaltante) {
        this.imp_totalFaltante = imp_totalFaltante;
    }
}
