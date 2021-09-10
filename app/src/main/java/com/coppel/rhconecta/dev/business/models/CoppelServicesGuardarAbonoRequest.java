package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarAbonoRequest extends CoppelServicesBaseFondoAhorroRequest {

    private Double imp_cuentacorriente = 0.0;
    private Double imp_ahorroadicional = 0.0;
    private Double imp_fondoempleado = 0.0;
    private int clv_retiro;
    private int idu_traspaso;

    public CoppelServicesGuardarAbonoRequest() {
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarAbonoRequest(Double imp_cuentacorriente, Double imp_ahorroadicional, Double imp_fondoempleado, int clv_retiro) {
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion, Double imp_cuentacorriente, Double imp_ahorroadicional, Double imp_fondoempleado, int clv_retiro, int idu_traspaso) {
        super(num_empleado, opcion);
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
        this.idu_traspaso = idu_traspaso;

    }

    public Double getImp_cuentacorriente() {
        return imp_cuentacorriente;
    }

    public void setImp_cuentacorriente(Double imp_cuentacorriente) {
        this.imp_cuentacorriente = imp_cuentacorriente;
    }

    public Double getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(Double imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public Double getImp_fondoempleado() {
        return imp_fondoempleado;
    }

    public void setImp_fondoempleado(Double imp_fondoempleado) {
        this.imp_fondoempleado = imp_fondoempleado;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }
}
