package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetMonthsExpensesRequest extends CoppelServicesBaseExpensesTravelRequest {

    private int clv_mes;

    public CoppelServicesGetMonthsExpensesRequest() {
    }

    public CoppelServicesGetMonthsExpensesRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGetMonthsExpensesRequest(int clv_mes) {
        this.clv_mes = clv_mes;
    }

    public CoppelServicesGetMonthsExpensesRequest(String num_empleado, int opcion, int clv_mes) {
        super(num_empleado, opcion);
        this.clv_mes = clv_mes;
    }

    public int getClv_mes() {
        return clv_mes;
    }

    public void setClv_mes(int clv_mes) {
        this.clv_mes = clv_mes;
    }
}
