package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetColaboratorsPeriodsHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private String num_gerente;

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest() {
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(String num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(int opcion, String num_gerente) {
        super(opcion);
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(Object num_empleado, int opcion, String num_gerente) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
    }

    public String getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(String num_gerente) {
        this.num_gerente = num_gerente;
    }
}
