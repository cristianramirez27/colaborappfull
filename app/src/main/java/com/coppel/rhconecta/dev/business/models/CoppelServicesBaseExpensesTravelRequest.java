package com.coppel.rhconecta.dev.business.models;

public abstract class CoppelServicesBaseExpensesTravelRequest {

    private String num_empleado;
    private int opcion;

    public CoppelServicesBaseExpensesTravelRequest() {
    }

    public CoppelServicesBaseExpensesTravelRequest(String num_empleado, int opcion) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
