package com.coppel.rhconecta.dev.visionarios.comunicados.objects;

import java.io.Serializable;

public class Comunicado implements Serializable {

    private int idavisos;
    private String nombre;
    private String encabezado;
    private String titulo;
    private String contenido;
    private String imagen_aviso_preview;
    private String imagen_aviso_landing;
    private String date;
    private int estatus;
    private int landing_visible;

    private boolean visto = false;

    private int opc_visto = 0;

    public  Comunicado(){}

    public Comunicado(int idavisos, String nombre, String encabezado, String titulo, String contenido, String imagen_aviso_preview, String imagen_aviso_landing, String date, int estatus) {
        this.idavisos = idavisos;
        this.nombre = nombre;
        this.encabezado = encabezado;
        this.titulo = titulo;
        this.contenido = contenido;
        this.imagen_aviso_preview = imagen_aviso_preview;
        this.imagen_aviso_landing = imagen_aviso_landing;
        this.date = date;
        this.estatus = estatus;

        this.visto = false;
    }

    public Comunicado(int idavisos, String nombre, String encabezado, String titulo, String contenido, String imagen_aviso_preview, String imagen_aviso_landing, String date, int estatus, int landing_visible, int iVisto) {
        this.idavisos = idavisos;
        this.nombre = nombre;
        this.encabezado = encabezado;
        this.titulo = titulo;
        this.contenido = contenido;
        this.imagen_aviso_preview = imagen_aviso_preview;
        this.imagen_aviso_landing = imagen_aviso_landing;
        this.date = date;
        this.estatus = estatus;
        this.landing_visible = landing_visible;
        this.opc_visto = iVisto;

        this.visto = false;
    }


    public int getLanding_visible() {
        return landing_visible;
    }

    public void setLanding_visible(int landing_visible) {
        this.landing_visible = landing_visible;
    }

    public int getIdavisos() {
        return idavisos;
    }

    public void setIdavisos(int idavisos) {
        this.idavisos = idavisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen_aviso_preview() {
        return imagen_aviso_preview;
    }

    public void setImagen_aviso_preview(String imagen_aviso_preview) {
        this.imagen_aviso_preview = imagen_aviso_preview;
    }

    public String getImagen_aviso_landing() {
        return imagen_aviso_landing;
    }

    public void setImagen_aviso_landing(String imagen_aviso_landing) {
        this.imagen_aviso_landing = imagen_aviso_landing;
    }

    public int getopc_visto() {
        return opc_visto;
    }

    public void setopc_visto(int opc_visto) {
        this.opc_visto = opc_visto;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
