package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class Observation implements Serializable {

        private String des_observacionesColaborador;
        private String des_observacionesGerente;


    public String getDes_observacionesColaborador() {
        return des_observacionesColaborador;
    }

    public void setDes_observacionesColaborador(String des_observacionesColaborador) {
        this.des_observacionesColaborador = des_observacionesColaborador;
    }

    public String getDes_observacionesGerente() {
        return des_observacionesGerente;
    }

    public void setDes_observacionesGerente(String des_observacionesGerente) {
        this.des_observacionesGerente = des_observacionesGerente;
    }
}
