package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarRetiroRequest extends CoppelServicesBaseFondoAhorroRequest {

    private int imp_margencredito;
    private int imp_ahorroadicional;

    public CoppelServicesGuardarRetiroRequest() {
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarRetiroRequest(int imp_margencredito, int imp_ahorroadicional) {
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion, int imp_margencredito, int imp_ahorroadicional) {
        super(num_empleado, opcion);
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public int getImp_margencredito() {
        return imp_margencredito;
    }

    public void setImp_margencredito(int imp_margencredito) {
        this.imp_margencredito = imp_margencredito;
    }

    public int getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(int imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }
}
