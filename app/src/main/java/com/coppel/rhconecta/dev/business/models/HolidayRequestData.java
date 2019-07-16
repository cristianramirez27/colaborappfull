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
}
