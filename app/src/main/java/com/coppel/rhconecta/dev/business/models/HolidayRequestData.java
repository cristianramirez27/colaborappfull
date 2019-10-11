package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.Enums.HolidaysType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HolidayRequestData implements Serializable{

    private HolidaysType holidaysType;
    private int opcion;
    private String num_empleado;
    private String num_empconsulta;
    private int num_gerente;
    private int num_suplente;
    private List<HolidayPeriodData> periodos;
    private  List<HolidayPeriodFolio> periodsChangeStatus;

    private int num_centro;
    private String nom_empleado;
    private int clv_estatus;

    private String des_observaciones;
    private int idu_folio;

    private int num_adicionales;
    private int idu_motivo;
    private String des_otromotivo;

    private String fec_ini;
    private String fec_fin;

    private int num_mes;
    private int num_anio;

    private int tipo_consulta;

    private String des_comentario;
    private int idu_autorizo;
    public HolidayRequestData(HolidaysType holidaysType, int opcion) {
        this.holidaysType = holidaysType;
        this.opcion = opcion;
    }

    public HolidayRequestData(HolidaysType holidaysType, int opcion, String num_empleado) {
        this.holidaysType = holidaysType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
    }

    public HolidaysType getHolidaysType() {
        return holidaysType;
    }

    public void setHolidaysType(HolidaysType holidaysType) {
        this.holidaysType = holidaysType;
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

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public int getNum_suplente() {
        return num_suplente;
    }

    public void setNum_suplente(int num_suplente) {
        this.num_suplente = num_suplente;
    }

    public List<HolidayPeriodData> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<HolidayPeriodData> periodos) {
        this.periodos = periodos;
    }

    public int getIdu_folio() {
        return idu_folio;
    }

    public void setIdu_folio(int idu_folio) {
        this.idu_folio = idu_folio;
    }

    public List<HolidayPeriodFolio> getPeriodsChangeStatus() {
        return periodsChangeStatus;
    }

    public void setPeriodsChangeStatus(List<HolidayPeriodFolio> periodsChangeStatus) {
        this.periodsChangeStatus = periodsChangeStatus;
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

    public int getNum_adicionales() {
        return num_adicionales;
    }

    public void setNum_adicionales(int num_adicionales) {
        this.num_adicionales = num_adicionales;
    }

    public int getIdu_motivo() {
        return idu_motivo;
    }

    public void setIdu_motivo(int idu_motivo) {
        this.idu_motivo = idu_motivo;
    }

    public String getDes_otromotivo() {
        return des_otromotivo;
    }

    public void setDes_otromotivo(String des_otromotivo) {
        this.des_otromotivo = des_otromotivo;
    }

    public String getNum_empconsulta() {
        return num_empconsulta;
    }

    public void setNum_empconsulta(String num_empconsulta) {
        this.num_empconsulta = num_empconsulta;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public String getDes_observaciones() {
        return des_observaciones;
    }

    public void setDes_observaciones(String des_observaciones) {
        this.des_observaciones = des_observaciones;
    }


    public int getNum_mes() {
        return num_mes;
    }

    public void setNum_mes(int num_mes) {
        this.num_mes = num_mes;
    }

    public int getNum_anio() {
        return num_anio;
    }

    public void setNum_anio(int num_anio) {
        this.num_anio = num_anio;
    }

    public String getDes_comentario() {
        return des_comentario;
    }

    public void setDes_comentario(String des_comentario) {
        this.des_comentario = des_comentario;
    }


    public int getIdu_autorizo() {
        return idu_autorizo;
    }

    public void setIdu_autorizo(int idu_autorizo) {
        this.idu_autorizo = idu_autorizo;
    }


    public int getTipo_consulta() {
        return tipo_consulta;
    }

    public void setTipo_consulta(int tipo_consulta) {
        this.tipo_consulta = tipo_consulta;
    }
}
