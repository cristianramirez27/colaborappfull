package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesReasonAditionalDaysHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    public CoppelServicesReasonAditionalDaysHolidaysRequest() {
    }

    public CoppelServicesReasonAditionalDaysHolidaysRequest(int opcion) {
        super(opcion);
    }

    public CoppelServicesReasonAditionalDaysHolidaysRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

}
