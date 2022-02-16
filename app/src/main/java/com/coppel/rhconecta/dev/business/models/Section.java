package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable {
    private int idu_seccion;
    private String seccion;
    private String seccion_nombre;
    private List<SubSection> subseccion;

    public int getIdu_seccion() {
        return idu_seccion;
    }

    public void setIdu_seccion(int idu_seccion) {
        this.idu_seccion = idu_seccion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getSeccion_nombre() {
        return seccion_nombre;
    }

    public void setSeccion_nombre(String seccion_nombre) {
        this.seccion_nombre = seccion_nombre;
    }

    public List<SubSection> getSubseccion() {
        return subseccion;
    }

    public void setSubseccion(List<SubSection> subeccion) {
        this.subseccion = subseccion;
    }
}
