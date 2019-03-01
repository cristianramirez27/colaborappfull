package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

/**
 * Created by faustolima on 29/11/18.
 */

public class LetterChildrenData  implements Serializable {

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
