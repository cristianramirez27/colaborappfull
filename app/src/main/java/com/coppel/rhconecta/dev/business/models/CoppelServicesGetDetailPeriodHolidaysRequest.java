package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetDetailPeriodHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int idu_folio;

    public CoppelServicesGetDetailPeriodHolidaysRequest() {
    }

    public CoppelServicesGetDetailPeriodHolidaysRequest(int idu_folio) {
        this.idu_folio = idu_folio;
    }

    public CoppelServicesGetDetailPeriodHolidaysRequest(Object num_empleado, int opcion, int idu_folio) {
        super(num_empleado, opcion);
        this.idu_folio = idu_folio;
    }
}
