package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsDiscountsRequest extends CoppelServicesBenefitsCategoriesRequest {

    private String clave_servicio;

    public CoppelServicesBenefitsDiscountsRequest(int solicitud, String num_estado, String num_ciudad, String clave_servicio) {
        super(solicitud, num_estado, num_ciudad);
        this.clave_servicio = clave_servicio;
    }

    public String getClave_servicio() {
        return clave_servicio;
    }

    public void setClave_servicio(String clave_servicio) {
        this.clave_servicio = clave_servicio;
    }
}
