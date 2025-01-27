package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesPayrollVoucherRequest {
    protected String num_empleado;
    protected int opcion;

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
