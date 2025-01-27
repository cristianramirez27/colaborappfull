package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsCategoriesLocationRequest extends CoppelServicesBenefitsStatesRequest {

    private String latitud;
    private String longitud;

    public CoppelServicesBenefitsCategoriesLocationRequest(int solicitud, String latitud, String longitud) {
        super(solicitud);
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
