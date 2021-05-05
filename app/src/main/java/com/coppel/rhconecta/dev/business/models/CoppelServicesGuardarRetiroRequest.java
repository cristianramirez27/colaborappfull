package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarRetiroRequest extends CoppelServicesBaseFondoAhorroRequest {

    private Double imp_margencredito = 0.0;
    private Double imp_ahorroadicional = 0.0;

    public CoppelServicesGuardarRetiroRequest() {
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarRetiroRequest(Double imp_margencredito, Double imp_ahorroadicional) {
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public CoppelServicesGuardarRetiroRequest(String num_empleado, int opcion, Double imp_margencredito, Double imp_ahorroadicional) {
        super(num_empleado, opcion);
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public Double getImp_margencredito() {
        return imp_margencredito;
    }

    public void setImp_margencredito(Double imp_margencredito) {
        this.imp_margencredito = imp_margencredito;
    }

    public Double getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(Double imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }
}
