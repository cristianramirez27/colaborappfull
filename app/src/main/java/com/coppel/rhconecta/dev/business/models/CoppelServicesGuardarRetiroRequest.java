package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarRetiroRequest extends CoppelServicesBaseFondoAhorroRequest {

    private String imp_margencredito = "0.0";
    private String imp_ahorroadicional = "0.0";

    public CoppelServicesGuardarRetiroRequest() {
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarRetiroRequest(String imp_margencredito, String imp_ahorroadicional) {
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion, String imp_margencredito, String imp_ahorroadicional) {
        super(num_empleado, opcion);
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public String getImp_margencredito() {
        return imp_margencredito;
    }

    public void setImp_margencredito(String imp_margencredito) {
        this.imp_margencredito = imp_margencredito;
    }

    public String getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(String imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }
}
