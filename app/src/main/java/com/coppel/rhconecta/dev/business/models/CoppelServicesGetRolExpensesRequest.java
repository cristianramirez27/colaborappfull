package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetRolExpensesRequest extends CoppelServicesBaseExpensesTravelRequest {

    public CoppelServicesGetRolExpensesRequest() {
    }

    public CoppelServicesGetRolExpensesRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

}
