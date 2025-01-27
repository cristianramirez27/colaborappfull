package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetColaboratorRequestExpensesRequest extends CoppelServicesBaseExpensesTravelRequest {

    public CoppelServicesGetColaboratorRequestExpensesRequest() {
    }

    public CoppelServicesGetColaboratorRequestExpensesRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

}
