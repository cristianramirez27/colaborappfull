package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGuardarAbonoRequest extends CoppelServicesBaseFondoAhorroRequest {

    private String imp_cuentacorriente = "0.0";
    private String imp_ahorroadicional = "0.0";
    private String imp_fondoempleado = "0.0";
    private int clv_retiro;
    private int idu_traspaso;

    public CoppelServicesGuardarAbonoRequest() {
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesGuardarAbonoRequest(String imp_cuentacorriente, String imp_ahorroadicional, String imp_fondoempleado, int clv_retiro) {
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
    }

    public CoppelServicesGuardarAbonoRequest(String num_empleado, int opcion, String imp_cuentacorriente, String imp_ahorroadicional, String imp_fondoempleado, int clv_retiro, int idu_traspaso) {
        super(num_empleado, opcion);
        this.imp_cuentacorriente = imp_cuentacorriente;
        this.imp_ahorroadicional = imp_ahorroadicional;
        this.imp_fondoempleado = imp_fondoempleado;
        this.clv_retiro = clv_retiro;
        this.idu_traspaso = idu_traspaso;

    }

    public String getImp_cuentacorriente() {
        return imp_cuentacorriente;
    }

    public void setImp_cuentacorriente(String imp_cuentacorriente) {
        this.imp_cuentacorriente = imp_cuentacorriente;
    }

    public String getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(String imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public String getImp_fondoempleado() {
        return imp_fondoempleado;
    }

    public void setImp_fondoempleado(String imp_fondoempleado) {
        this.imp_fondoempleado = imp_fondoempleado;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }
}
