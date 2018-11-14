package com.coppel.rhconecta.dev.visionarios.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

/**
 * 6 Noviembre 2018
 * Modificacion: se modificó la clase.
 * **/
public class ComunicadosFirebase {

    public String id;
    public int showed;

    public ComunicadosFirebase(String id, int showed) {
        this.id = id;
        this.showed = showed;
    }

    public ComunicadosFirebase() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getShowed() {
        return showed;
    }

    public void setShowed(int showed) {
        this.showed = showed;
    }
}