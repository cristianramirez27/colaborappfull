package com.coppel.rhconecta.dev.business.models;

import com.facebook.stetho.common.StringUtil;

public class BenefitCodeRequest {
    private int opc;
    private String numEmpleado;
    private String idempresa;
    private int solicitud;

    public BenefitCodeRequest() {
    }

    public BenefitCodeRequest(int opc, String numEmpleado, String idempresa,int solicitud) {
        this.opc = opc;
        this.numEmpleado = numEmpleado;
        this.idempresa = idempresa;
        this.solicitud = solicitud;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

}
