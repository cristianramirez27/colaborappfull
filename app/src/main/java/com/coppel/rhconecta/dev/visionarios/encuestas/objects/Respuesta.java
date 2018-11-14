package com.coppel.rhconecta.dev.visionarios.encuestas.objects;

import java.io.Serializable;

public class Respuesta implements Serializable {
    private int idrespuesta;
    private String contenido;
    private boolean seleccionado = false;

    public Respuesta(int idrespuesta, String contenido) {
        this.idrespuesta = idrespuesta;
        this.contenido = contenido;
    }

    public int getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(int idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
