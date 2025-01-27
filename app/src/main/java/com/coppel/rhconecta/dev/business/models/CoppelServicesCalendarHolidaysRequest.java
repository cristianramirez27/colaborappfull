package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CoppelServicesCalendarHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;
    private int num_mes;
    private int num_anio;
    private int num_centro;

    private String nom_empleado;


    public CoppelServicesCalendarHolidaysRequest(Object num_empleado, int opcion, int num_gerente, int num_mes, int num_anio,int num_centro) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.num_mes = num_mes;
        this.num_anio = num_anio;
        this.num_centro = num_centro;
    }

    public CoppelServicesCalendarHolidaysRequest() {
    }

    public CoppelServicesCalendarHolidaysRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
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
}
