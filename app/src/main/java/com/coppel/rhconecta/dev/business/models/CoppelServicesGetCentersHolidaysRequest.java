package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetCentersHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;

    public CoppelServicesGetCentersHolidaysRequest() {
    }

    public CoppelServicesGetCentersHolidaysRequest(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetCentersHolidaysRequest( int opcion, int num_gerente) {
        super( opcion);
        this.num_gerente = num_gerente;
    }


}
