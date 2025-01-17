package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetColaboratorsPeriodsHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest() {
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(int opcion, int num_gerente) {
        super(opcion);
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetColaboratorsPeriodsHolidaysRequest(Object num_empleado, int opcion, int num_gerente) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }
}
