package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesBenefitsSearchRequest extends CoppelServicesBenefitsCategoriesRequest {

    private String des_busqueda;

    public CoppelServicesBenefitsSearchRequest(int solicitud, String num_estado, String num_ciudad, String des_busqueda) {
        super(solicitud, num_estado, num_ciudad);
        this.des_busqueda = des_busqueda;
    }

    public String getDes_busqueda() {
        return des_busqueda;
    }

    public void setDes_busqueda(String des_busqueda) {
        this.des_busqueda = des_busqueda;
    }
}
