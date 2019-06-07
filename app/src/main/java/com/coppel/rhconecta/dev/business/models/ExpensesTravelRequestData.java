package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.ExpensesTravelType;
import com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType;

import java.io.Serializable;

public class ExpensesTravelRequestData implements Serializable{

    private ExpensesTravelType expensesTravelType;
    private int opcion;
    private String num_empleado;

    private int clv_mes;
    private int clv_solicitud;
    private int clv_control;

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
}
