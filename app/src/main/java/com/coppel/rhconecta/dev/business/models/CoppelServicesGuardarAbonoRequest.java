package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarAbonoRequest extends CoppelServicesBaseFondoAhorroRequest {

    private int imp_cuentacorriente;
    private int imp_ahorroadicional;
    private int imp_fondoempleado;
    private int clv_retiro;

    public CoppelServicesGuardarAbonoRequest() {
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarAbonoRequest(int imp_cuentacorriente, int imp_ahorroadicional, int imp_fondoempleado, int clv_retiro) {
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion, int imp_cuentacorriente, int imp_ahorroadicional, int imp_fondoempleado, int clv_retiro) {
        super(num_empleado, opcion);
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
    }

    public int getImp_cuentacorriente() {
        return imp_cuentacorriente;
    }

    public void setImp_cuentacorriente(int imp_cuentacorriente) {
        this.imp_cuentacorriente = imp_cuentacorriente;
    }

    public int getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(int imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public int getImp_fondoempleado() {
        return imp_fondoempleado;
    }

    public void setImp_fondoempleado(int imp_fondoempleado) {
        this.imp_fondoempleado = imp_fondoempleado;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }
}
