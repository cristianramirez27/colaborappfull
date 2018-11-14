package com.coppel.rhconecta.dev.visionarios.encuestas.objects;

import java.io.Serializable;

public class Encuesta implements Serializable {
    private int idencuestas;
    private String nombre;
    private String img_encuesta_preview;
    private String img_encuesta_landing;
    private int estatus;
    private boolean visto = false;

    public Encuesta() {
    }

    public Encuesta(int idencuestas, String nombre, String img_encuesta_preview, String img_encuesta_landing, int estatus) {
        this.idencuestas = idencuestas;
        this.nombre = nombre;
        this.img_encuesta_preview = img_encuesta_preview;
        this.img_encuesta_landing = img_encuesta_landing;
        this.estatus = estatus;
        this.visto = false;

    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public int getIdencuestas() {
        return idencuestas;
    }

    public void setIdencuestas(int idencuestas) {
        this.idencuestas = idencuestas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg_encuesta_preview() {
        return img_encuesta_preview;
    }

    public void setImg_encuesta_preview(String img_encuesta_preview) {
        this.img_encuesta_preview = img_encuesta_preview;
    }

    public String getImg_encuesta_landing() {
        return img_encuesta_landing;
    }

    public void setImg_encuesta_landing(String img_encuesta_landing) {
        this.img_encuesta_landing = img_encuesta_landing;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}

