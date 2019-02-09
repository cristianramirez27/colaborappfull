package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsBaseRequest {

    private int solicitud;

    public CoppelServicesBenefitsBaseRequest(int solicitud) {
        this.solicitud = solicitud;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }
}
