package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsAdvertisingRequest extends CoppelServicesBenefitsBaseRequest {

    private String idempresa;

    public CoppelServicesBenefitsAdvertisingRequest(int solicitud, String idempresa) {
        super(solicitud);
        this.idempresa = idempresa;
    }

    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }
}
