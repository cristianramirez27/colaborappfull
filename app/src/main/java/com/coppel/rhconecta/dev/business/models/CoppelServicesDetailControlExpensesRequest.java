package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesDetailControlExpensesRequest extends CoppelServicesBaseExpensesTravelRequest {

    private int clv_control;

    public CoppelServicesDetailControlExpensesRequest() {
    }

    public CoppelServicesDetailControlExpensesRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesDetailControlExpensesRequest(int clv_control) {
        this.clv_control = clv_control;
    }

    public CoppelServicesDetailControlExpensesRequest(String num_empleado, int opcion, int clv_control) {
        super(num_empleado, opcion);
        this.clv_control = clv_control;
    }

    public int getClv_control() {
        return clv_control;
    }

    public void setClv_control(int clv_control) {
        this.clv_control = clv_control;
    }
}
