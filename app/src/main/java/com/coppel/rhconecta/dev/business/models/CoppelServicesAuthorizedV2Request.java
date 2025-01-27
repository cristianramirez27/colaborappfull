package com.coppel.rhconecta.dev.business.models;

import java.util.ArrayList;
import java.util.List;

public class CoppelServicesAuthorizedV2Request extends CoppelServicesBaseExpensesTravelRequest {


    private int num_gerente;
    private int clv_solicitud;
    private int num_control;
    private int clv_estatus;
    private String des_observaciones;
    private String des_motivoRechazo;
    private  int imp_avion;
    private  int imp_transporte;
    private  int imp_hospedaje;
    private  int imp_alimentacion;
    private  int imp_taxi;
    private  int imp_lavanderia;
    private  int imp_otros;
    private  int imp_total;
    private  int clv_tipo;
    private List<DetailRequestFormatAuthorize> CapturaGerente;


    public CoppelServicesAuthorizedV2Request() {
    }

    public CoppelServicesAuthorizedV2Request(Object num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesAuthorizedV2Request(CoppelServicesAuthorizedRequest coppelServicesAuthorizedRequest,List<DetailRequestAuthorize> data ) {
        super(coppelServicesAuthorizedRequest.getNum_empleado(), coppelServicesAuthorizedRequest.getOpcion());
        this.num_gerente = coppelServicesAuthorizedRequest.getNum_gerente();
        this.clv_solicitud = coppelServicesAuthorizedRequest.getClv_solicitud();
        this.num_control = coppelServicesAuthorizedRequest.getNum_control();
        this.clv_estatus = coppelServicesAuthorizedRequest.getClv_estatus();
        this.des_observaciones = coppelServicesAuthorizedRequest.getDes_observaciones();
        this.des_motivoRechazo = coppelServicesAuthorizedRequest.getDes_motivoRechazo();
        this.clv_tipo = coppelServicesAuthorizedRequest.getClv_tipo();
        this.CapturaGerente =  processAmounts(data);
    }


    private List<DetailRequestFormatAuthorize> processAmounts(List<DetailRequestAuthorize> data){
        List<DetailRequestFormatAuthorize> dataFormat = new ArrayList<>();
        for(DetailRequestAuthorize authorize : data){

            String impAsString = String.format("%.2f",authorize.getImp_total());

            impAsString = impAsString.replace(".","");
            int value = Integer.parseInt(impAsString);
            setImports(authorize.getIdu_tipoGasto(),value);
            dataFormat.add(new DetailRequestFormatAuthorize(authorize.getIdu_tipoGasto(),value));
        }

        return dataFormat;

    }

    private void setImports(int idu_tipoGasto,int amount){

        //private  double imp_avion;
        //double amount = Double.parseDouble(value);

        switch (idu_tipoGasto){

            case -1:
                this.imp_total = amount;
                break;

            case 1:
                this.imp_avion = amount;
                break;

            case 2:
                this.imp_transporte = amount;
                break;

            case 3:
                this.imp_hospedaje = amount;
                break;

            case 4:
                this.imp_alimentacion = amount;
                break;

            case 5:
                this.imp_otros = amount;
                break;

            case 6:
                this.imp_taxi = amount;
                break;

            case 7:
                this.imp_lavanderia = amount;
                break;
        }
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

    public int getImp_avion() {
        return imp_avion;
    }

    public void setImp_avion(int imp_avion) {
        this.imp_avion = imp_avion;
    }

    public int getImp_transporte() {
        return imp_transporte;
    }

    public void setImp_transporte(int imp_transporte) {
        this.imp_transporte = imp_transporte;
    }

    public int getImp_hospedaje() {
        return imp_hospedaje;
    }

    public void setImp_hospedaje(int imp_hospedaje) {
        this.imp_hospedaje = imp_hospedaje;
    }

    public int getImp_alimentacion() {
        return imp_alimentacion;
    }

    public void setImp_alimentacion(int imp_alimentacion) {
        this.imp_alimentacion = imp_alimentacion;
    }

    public int getImp_taxi() {
        return imp_taxi;
    }

    public void setImp_taxi(int imp_taxi) {
        this.imp_taxi = imp_taxi;
    }

    public int getImp_lavanderia() {
        return imp_lavanderia;
    }

    public void setImp_lavanderia(int imp_lavanderia) {
        this.imp_lavanderia = imp_lavanderia;
    }

    public int getImp_otros() {
        return imp_otros;
    }

    public void setImp_otros(int imp_otros) {
        this.imp_otros = imp_otros;
    }

    public int getImp_total() {
        return imp_total;
    }

    public void setImp_total(int imp_total) {
        this.imp_total = imp_total;
    }

    public int getClv_tipo() {
        return clv_tipo;
    }

    public void setClv_tipo(int clv_tipo) {
        this.clv_tipo = clv_tipo;
    }

    public List<DetailRequestFormatAuthorize> getCapturaGerente() {
        return CapturaGerente;
    }

    public void setCapturaGerente(List<DetailRequestFormatAuthorize> capturaGerente) {
        CapturaGerente = capturaGerente;
    }
}
