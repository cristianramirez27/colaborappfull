package com.coppel.rhconecta.dev.business.models;

public class BenefitCodeRequest {
    private int opc;
    private int numEmpleado;
    private int numEmpresa;

    public BenefitCodeRequest(){}

    public BenefitCodeRequest(int opc, int numEmpleado, int numEmpresa){
        this.opc = opc;
        this.numEmpleado = numEmpleado;
        this.numEmpresa = numEmpresa;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public int getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(int numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }
}
