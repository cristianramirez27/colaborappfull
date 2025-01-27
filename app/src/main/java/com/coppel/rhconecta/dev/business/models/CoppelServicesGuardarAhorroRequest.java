package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarAhorroRequest extends CoppelServicesBaseFondoAhorroRequest {

    private int imp_cuotaahorro;

    public CoppelServicesGuardarAhorroRequest() {
    }

    public CoppelServicesGuardarAhorroRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarAhorroRequest(int imp_cuotaahorro) {
        this.imp_cuotaahorro = imp_cuotaahorro;
    }

    public CoppelServicesGuardarAhorroRequest(String num_empleado, int opcion, int imp_cuotaahorro) {
        super(num_empleado, opcion);
        this.imp_cuotaahorro = imp_cuotaahorro;
    }

    public int getImp_cuotaahorro() {
        return imp_cuotaahorro;
    }

    public void setImp_cuotaahorro(int imp_cuotaahorro) {
        this.imp_cuotaahorro = imp_cuotaahorro;
    }
}
