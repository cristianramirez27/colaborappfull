package com.coppel.rhconecta.dev.business.models;

public class ValidateDeviceIdRequest {
    private int opcion;
    private int empleado;
    private String id;

    public ValidateDeviceIdRequest(){}

    public ValidateDeviceIdRequest(int opcion,int empleado, String id){
        this.opcion = opcion;
        this.empleado = empleado;
        this.id = id;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
