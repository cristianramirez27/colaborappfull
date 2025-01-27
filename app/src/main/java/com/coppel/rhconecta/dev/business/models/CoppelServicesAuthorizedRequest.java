package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CoppelServicesAuthorizedRequest extends CoppelServicesBaseExpensesTravelRequest {


    private int num_gerente;
    private int clv_solicitud;
    private int num_control;
    private int clv_estatus;
    private String des_observaciones;
    private String des_motivoRechazo;
    private  double imp_avion;
    private  double imp_transporte;
    private  double imp_hospedaje;
    private  double imp_alimentacion;
    private  double imp_taxi;
    private  double imp_lavanderia;
    private  double imp_otros;
    private  double imp_total;
    private  int clv_tipo;
    private List<DetailRequest> CapturaGerente;


    public CoppelServicesAuthorizedRequest() {
    }

    public CoppelServicesAuthorizedRequest(Object num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesAuthorizedRequest(int num_gerente, int clv_solicitud, int num_control, int clv_estatus, String des_observaciones, String des_motivoRechazo, int clv_tipo, List<DetailRequest> capturaGerente) {
        this.num_gerente = num_gerente;
        this.clv_solicitud = clv_solicitud;
        this.num_control = num_control;
        this.clv_estatus = clv_estatus;
        this.des_observaciones = des_observaciones;
        this.des_motivoRechazo = des_motivoRechazo;
        this.clv_tipo = clv_tipo;
        CapturaGerente = capturaGerente;
    }

    public CoppelServicesAuthorizedRequest(Object num_empleado, int opcion, int num_gerente, int clv_solicitud, int num_control, int clv_estatus, String des_observaciones, String des_motivoRechazo, int clv_tipo, List<DetailRequest> capturaGerente) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.clv_solicitud = clv_solicitud;
        this.num_control = num_control;
        this.clv_estatus = clv_estatus;
        this.des_observaciones = des_observaciones;
        this.des_motivoRechazo = des_motivoRechazo;
        this.clv_tipo = clv_tipo;
        CapturaGerente = capturaGerente;
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

    public double getImp_avion() {
        return imp_avion;
    }

    public void setImp_avion(double imp_avion) {
        this.imp_avion = imp_avion;
    }

    public double getImp_transporte() {
        return imp_transporte;
    }

    public void setImp_transporte(double imp_transporte) {
        this.imp_transporte = imp_transporte;
    }

    public double getImp_hospedaje() {
        return imp_hospedaje;
    }

    public void setImp_hospedaje(double imp_hospedaje) {
        this.imp_hospedaje = imp_hospedaje;
    }

    public double getImp_alimentacion() {
        return imp_alimentacion;
    }

    public void setImp_alimentacion(double imp_alimentacion) {
        this.imp_alimentacion = imp_alimentacion;
    }

    public double getImp_taxi() {
        return imp_taxi;
    }

    public void setImp_taxi(double imp_taxi) {
        this.imp_taxi = imp_taxi;
    }

    public double getImp_lavanderia() {
        return imp_lavanderia;
    }

    public void setImp_lavanderia(double imp_lavanderia) {
        this.imp_lavanderia = imp_lavanderia;
    }

    public double getImp_otros() {
        return imp_otros;
    }

    public void setImp_otros(double imp_otros) {
        this.imp_otros = imp_otros;
    }

    public double getImp_total() {
        return imp_total;
    }

    public void setImp_total(double imp_total) {
        this.imp_total = imp_total;
    }

    public int getClv_tipo() {
        return clv_tipo;
    }

    public void setClv_tipo(int clv_tipo) {
        this.clv_tipo = clv_tipo;
    }

    public List<DetailRequest> getCapturaGerente() {
        return CapturaGerente;
    }

    public void setCapturaGerente(List<DetailRequest> capturaGerente) {
        CapturaGerente = capturaGerente;
    }
}
