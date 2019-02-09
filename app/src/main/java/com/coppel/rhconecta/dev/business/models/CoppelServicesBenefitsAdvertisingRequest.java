package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsAdvertisingRequest extends CoppelServicesBenefitsBaseRequest {

    private int idempresa;

    public CoppelServicesBenefitsAdvertisingRequest(int solicitud, int idempresa) {
        super(solicitud);
        this.idempresa = idempresa;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }
}
