package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetColaboratorsHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;
    private int num_centro;
    private String nom_empleado;
    private int clv_estatus;

    public CoppelServicesGetColaboratorsHolidaysRequest() {
    }

    public CoppelServicesGetColaboratorsHolidaysRequest(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetColaboratorsHolidaysRequest(int opcion, int num_gerente) {
        super( opcion);
        this.num_gerente = num_gerente;
    }


    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public int getNum_centro() {
        return num_centro;
    }

    public void setNum_centro(int num_centro) {
        this.num_centro = num_centro;
    }

    public String getNom_empleado() {
        return nom_empleado;
    }

    public void setNom_empleado(String nom_empleado) {
        this.nom_empleado = nom_empleado;
    }

    public int getClv_estatus() {
        return clv_estatus;
    }

    public void setClv_estatus(int clv_estatus) {
        this.clv_estatus = clv_estatus;
    }
}
