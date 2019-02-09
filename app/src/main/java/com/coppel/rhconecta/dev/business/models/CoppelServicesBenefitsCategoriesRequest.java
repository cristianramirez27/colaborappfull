package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsCategoriesRequest extends CoppelServicesBenefitsCityRequest {

    private String num_ciudad;

    public CoppelServicesBenefitsCategoriesRequest(int solicitud, String num_estado, String num_ciudad) {
        super(solicitud, num_estado);
        this.num_ciudad = num_ciudad;
    }

    public String getNum_ciudad() {
        return num_ciudad;
    }

    public void setNum_ciudad(String num_ciudad) {
        this.num_ciudad = num_ciudad;
    }
}
