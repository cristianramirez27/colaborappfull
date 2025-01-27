package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpensesTravelRequestData implements Serializable{

    private ExpensesTravelType expensesTravelType;
    private int opcion;
    private String num_empleado;

    private int clv_mes;
    private int clv_solicitud;
    private int clv_control;

    private int clv_estatus;
    private String nom_empleado = "";
    private int num_centro;

    private int num_gerente;
    private int num_control;
    private String des_observaciones;
    private String des_motivoRechazo;
    private int clv_tipo;


    private List<DetailRequest> CapturaGerente;

    private List<DetailRequestAuthorize> CapturaGerenteFormat;


    public ExpensesTravelRequestData(ExpensesTravelType expensesTravelType, int opcion, String num_empleado) {
        this.expensesTravelType = expensesTravelType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
    }

    public ExpensesTravelType getExpensesTravelType() {
        return expensesTravelType;
    }

    public void setExpensesTravelType(ExpensesTravelType expensesTravelType) {
        this.expensesTravelType = expensesTravelType;
    }

    public ExpensesTravelRequestData(ExpensesTravelType expensesTravelType, int opcion, String num_empleado, int clv_estatus, String nom_empleado, int num_centro) {
        this.expensesTravelType = expensesTravelType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
        this.clv_estatus = clv_estatus;
        this.nom_empleado = nom_empleado;
        this.num_centro = num_centro;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getClv_mes() {
        return clv_mes;
    }

    public void setClv_mes(int clv_mes) {
        this.clv_mes = clv_mes;
    }

    public int getClv_solicitud() {
        return clv_solicitud;
    }

    public void setClv_solicitud(int clv_solicitud) {
        this.clv_solicitud = clv_solicitud;
    }

    public int getClv_control() {
        return clv_control;
    }

    public void setClv_control(int clv_control) {
        this.clv_control = clv_control;
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

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public int getNum_control() {
        return num_control;
    }

    public void setNum_control(int num_control) {
        this.num_control = num_control;
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

    public List<DetailRequest> getCapturaGerente() {
        return CapturaGerente;
    }

    public void setCapturaGerente(List<DetailRequest> capturaGerente) {
        CapturaGerente = capturaGerente;
    }

    public List<DetailRequestAuthorize> getCapturaGerenteFormat() {
        return CapturaGerenteFormat;
    }

    public void setCapturaGerenteFormat(List<DetailRequest> capturaGerente) {

        CapturaGerenteFormat = new ArrayList<>();
        for(DetailRequest detailRequest : capturaGerente){
            String amount = detailRequest.getImp_total().replace(",","");
            CapturaGerenteFormat.add(new DetailRequestAuthorize(detailRequest.getIdu_tipoGasto(),Double.parseDouble(amount)));
        }

    }
}
