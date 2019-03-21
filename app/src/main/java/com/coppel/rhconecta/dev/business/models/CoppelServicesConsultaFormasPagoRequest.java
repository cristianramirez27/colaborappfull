package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesConsultaFormasPagoRequest extends CoppelServicesBaseFondoAhorroRequest {

    private int clv_abonar;

    public CoppelServicesConsultaFormasPagoRequest() {
    }

    public CoppelServicesConsultaFormasPagoRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesConsultaFormasPagoRequest(int clv_abonar) {
        this.clv_abonar = clv_abonar;
    }

    public CoppelServicesConsultaFormasPagoRequest(String num_empleado, int opcion, int clv_abonar) {
        super(num_empleado, opcion);
        this.clv_abonar = clv_abonar;
    }

    public int getClv_abonar() {
        return clv_abonar;
    }

    public void setClv_abonar(int clv_abonar) {
        this.clv_abonar = clv_abonar;
    }
}
