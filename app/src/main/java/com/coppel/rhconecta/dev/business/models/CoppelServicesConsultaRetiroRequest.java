package com.coppel.rhconecta.dev.business.models;

import java.util.Map;

public class CoppelServicesConsultaRetiroRequest extends CoppelServicesBaseFondoAhorroRequest {

    public CoppelServicesConsultaRetiroRequest() {
    }

    public CoppelServicesConsultaRetiroRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

}
