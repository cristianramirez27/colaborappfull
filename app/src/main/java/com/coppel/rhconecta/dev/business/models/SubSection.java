package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class SubSection implements Serializable {
    private int idu_subseccion;
    private String cve_subseccion;
    private String subseccion_nombre;

    public int getIdu_subseccion() {
        return idu_subseccion;
    }

    public void setIdu_subseccion(int idu_subseccion) {
        this.idu_subseccion = idu_subseccion;
    }

    public String getCve_subseccion() {
        return cve_subseccion;
    }

    public void setCve_subseccion(String cve_subseccion) {
        this.cve_subseccion = cve_subseccion;
    }

    public String getSubseccion_nombre() {
        return subseccion_nombre;
    }

    public void setSubseccion_nombre(String subseccion_nombre) {
        this.subseccion_nombre = subseccion_nombre;
    }
}
