package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesLoanSavingFundRequest {
    private String num_empleado;
    private int opcion;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public CoppelServicesLoanSavingFundRequest(String num_empleado, int opcion) {
        this.num_empleado = num_empleado;
        this.opcion = opcion;
    }
}
