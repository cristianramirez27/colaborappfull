package com.coppel.rhconecta.dev.business.models;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterChildrenData {

    private String nombre_completo;

    public LetterChildrenData(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }
}
