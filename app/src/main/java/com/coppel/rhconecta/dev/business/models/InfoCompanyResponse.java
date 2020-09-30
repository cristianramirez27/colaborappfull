package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class InfoCompanyResponse implements Serializable {
    private String iempresa;
    private String irequisitos;
    private String idescuento;
    private String inota;
    private String itelefono;
    private int iopc_desc_app;
    private int inum_duraciondescuento;

    public String getEmpresa() {
        return iempresa;
    }

    public void setEmpresa(String iempresa) {
        this.iempresa = iempresa;
    }

    public String getRequisitos() {
        return irequisitos;
    }

    public void setRequisitos(String irequisitos) {
        this.irequisitos = irequisitos;
    }

    public String getDescuento() {
        return idescuento;
    }

    public void setDescuento(String idescuento) {
        this.idescuento = idescuento;
    }

    public String getNota() {
        return inota;
    }

    public void setNota(String inota) {
        this.inota = inota;
    }

    public String getTelefono() {
        return itelefono;
    }

    public void setTelefono(String itelefono) {
        this.itelefono = itelefono;
    }

    public int getOpc_desc_app() {
        return iopc_desc_app;
    }

    public void setOpc_desc_app(int iopc_desc_app) {
        this.iopc_desc_app = iopc_desc_app;
    }

    public int getNum_duraciondescuento() {
        return inum_duraciondescuento;
    }

    public void setNum_duraciondescuento(int inum_duraciondescuento) {
        this.inum_duraciondescuento = inum_duraciondescuento;
    }
}
