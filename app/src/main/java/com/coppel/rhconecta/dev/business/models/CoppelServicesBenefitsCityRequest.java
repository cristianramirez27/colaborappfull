package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsCityRequest extends CoppelServicesBenefitsStatesRequest {

    private String num_estado;

    public CoppelServicesBenefitsCityRequest(int solicitud, String num_estado) {
        super(solicitud);
        this.num_estado = num_estado;
    }


    public String getNum_estado() {
        return num_estado;
    }

    public void setNum_estado(String num_estado) {
        this.num_estado = num_estado;
    }
}
