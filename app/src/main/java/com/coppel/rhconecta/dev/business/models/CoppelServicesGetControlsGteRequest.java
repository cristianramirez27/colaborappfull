package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetControlsGteRequest extends CoppelServicesBaseExpensesTravelRequest {

    private int clv_estatus;
    private String nom_empleado;
    private int num_centro;

    public CoppelServicesGetControlsGteRequest(int clv_estatus, String nom_empleado, int num_centro) {
        this.clv_estatus = clv_estatus;
        this.nom_empleado = nom_empleado;
        this.num_centro = num_centro;
    }

    public CoppelServicesGetControlsGteRequest(Object num_empleado, int opcion, int clv_estatus, String nom_empleado, int num_centro) {
        super(num_empleado, opcion);
        this.clv_estatus = clv_estatus;
        this.nom_empleado = nom_empleado;
        this.num_centro = num_centro;
    }

    public int getClv_estatus() {
        return clv_estatus;
    }

    public void setClv_estatus(int clv_estatus) {
        this.clv_estatus = clv_estatus;
    }

    public String getNom_empleado() {
        return nom_empleado;
    }

    public void setNom_empleado(String nom_empleado) {
        this.nom_empleado = nom_empleado;
    }

    public int getNum_centro() {
        return num_centro;
    }

    public void setNum_centro(int num_centro) {
        this.num_centro = num_centro;
    }
}
