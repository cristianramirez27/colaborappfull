package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesCollageUrlRequest extends CoppelServicesBaseCollageRequest{

    public CoppelServicesCollageUrlRequest() {
    }

    public CoppelServicesCollageUrlRequest(int opcion) {
        super(opcion);
    }

    public CoppelServicesCollageUrlRequest(Object num_empleado, int opcion) {
        super(num_empleado, opcion);
    }
}
