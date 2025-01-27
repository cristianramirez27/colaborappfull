package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesDetailRequestExpensesRequest extends CoppelServicesBaseExpensesTravelRequest {

    private int clv_solicitud;

    public CoppelServicesDetailRequestExpensesRequest() {
    }

    public CoppelServicesDetailRequestExpensesRequest(Object num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesDetailRequestExpensesRequest(int clv_solicitud) {
        this.clv_solicitud = clv_solicitud;
    }

    public CoppelServicesDetailRequestExpensesRequest(Object num_empleado, int opcion, int clv_solicitud) {
        super(num_empleado, opcion);
        this.clv_solicitud = clv_solicitud;
    }

    public int getClv_solicitud() {
        return clv_solicitud;
    }

    public void setClv_solicitud(int clv_solicitud) {
        this.clv_solicitud = clv_solicitud;
    }
}
