package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesRefuseRequest extends CoppelServicesBaseExpensesTravelRequest {


    private int num_gerente;
    private int clv_solicitud;
    private int num_control;
    private int clv_estatus;
    private String des_observaciones;
    private String des_motivoRechazo;
    private  int clv_tipo;

    public CoppelServicesRefuseRequest(int num_gerente, int clv_solicitud, int num_control, int clv_estatus, String des_observaciones, String des_motivoRechazo, int clv_tipo) {
        this.num_gerente = num_gerente;
        this.clv_solicitud = clv_solicitud;
        this.num_control = num_control;
        this.clv_estatus = clv_estatus;
        this.des_observaciones = des_observaciones;
        this.des_motivoRechazo = des_motivoRechazo;
        this.clv_tipo = clv_tipo;
    }

    public CoppelServicesRefuseRequest(Object num_empleado, int opcion, int num_gerente, int clv_solicitud, int num_control, int clv_estatus, String des_observaciones, String des_motivoRechazo, int clv_tipo) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.clv_solicitud = clv_solicitud;
        this.num_control = num_control;
        this.clv_estatus = clv_estatus;
        this.des_observaciones = des_observaciones;
        this.des_motivoRechazo = des_motivoRechazo;
        this.clv_tipo = clv_tipo;
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public int getClv_solicitud() {
        return clv_solicitud;
    }

    public void setClv_solicitud(int clv_solicitud) {
        this.clv_solicitud = clv_solicitud;
    }

    public int getNum_control() {
        return num_control;
    }

    public void setNum_control(int num_control) {
        this.num_control = num_control;
    }

    public int getClv_estatus() {
        return clv_estatus;
    }

    public void setClv_estatus(int clv_estatus) {
        this.clv_estatus = clv_estatus;
    }

    public String getDes_observaciones() {
        return des_observaciones;
    }

    public void setDes_observaciones(String des_observaciones) {
        this.des_observaciones = des_observaciones;
    }

    public String getDes_motivoRechazo() {
        return des_motivoRechazo;
    }

    public void setDes_motivoRechazo(String des_motivoRechazo) {
        this.des_motivoRechazo = des_motivoRechazo;
    }

    public int getClv_tipo() {
        return clv_tipo;
    }

    public void setClv_tipo(int clv_tipo) {
        this.clv_tipo = clv_tipo;
    }
}
