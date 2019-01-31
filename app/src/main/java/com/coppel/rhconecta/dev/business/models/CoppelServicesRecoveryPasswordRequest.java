package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesRecoveryPasswordRequest {

    private int solicitud;
    private int clave;

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int opcion) {
        this.solicitud = opcion;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }
}
