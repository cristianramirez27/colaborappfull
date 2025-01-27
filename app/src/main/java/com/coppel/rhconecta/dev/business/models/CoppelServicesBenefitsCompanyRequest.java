package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsCompanyRequest extends CoppelServicesBenefitsDiscountsRequest {

    private String idempresa;


    public CoppelServicesBenefitsCompanyRequest(int solicitud, String num_estado, String num_ciudad, String clave_servicio, String idempresa) {
        super(solicitud, num_estado, num_ciudad, clave_servicio);
        this.idempresa = idempresa;
    }

    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }
}
