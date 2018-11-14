package com.coppel.rhconecta.dev.visionarios.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * 6 Noviembre 2018
 * Modificacion: se modificó la clase.
 * **/

@IgnoreExtraProperties

public class VideosFirebase {
    public String id;
    public int showed;

    public VideosFirebase() {
    }

    public VideosFirebase(String id, int showed) {
        this.id = id;
        this.showed = showed;
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