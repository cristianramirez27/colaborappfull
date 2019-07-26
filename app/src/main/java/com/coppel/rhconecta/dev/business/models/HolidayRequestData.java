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

    private int idu_folio;



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
}
