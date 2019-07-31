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

    private int num_gerente;
    private int num_suplente;
    private List<HolidayPeriodData> periodos;
    private  List<HolidayPeriodFolio> periodsToCancel;

    private int num_centro;
    private String nom_empleado;
    private int clv_estatus;


    private int idu_folio;

    private int num_adicionales;
    private int idu_motivo;
    private String des_otromotivo;

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

    public List<HolidayPeriodFolio> getPeriodsToCancel() {
        return periodsToCancel;
    }

    public void setPeriodsToCancel(List<HolidayPeriodFolio> periodsToCancel) {
        this.periodsToCancel = periodsToCancel;
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
}
