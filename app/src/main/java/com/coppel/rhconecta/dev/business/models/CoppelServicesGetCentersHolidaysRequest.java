package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetCentersHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private String num_gerente;

    public CoppelServicesGetCentersHolidaysRequest() {
    }

    public CoppelServicesGetCentersHolidaysRequest(String num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetCentersHolidaysRequest( int opcion, String num_gerente) {
        super( opcion);
        this.num_gerente = num_gerente;
    }


}
