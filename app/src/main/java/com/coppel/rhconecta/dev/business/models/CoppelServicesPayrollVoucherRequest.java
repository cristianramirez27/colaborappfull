package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesPayrollVoucherRequest {
    protected String num_empleado;
    protected int solicitud;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }
}
