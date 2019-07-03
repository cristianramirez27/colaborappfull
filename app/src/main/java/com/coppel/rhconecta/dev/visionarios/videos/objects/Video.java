package com.coppel.rhconecta.dev.visionarios.videos.objects;

import java.io.Serializable;

public class Video implements Serializable {

    private int idvideos;
    private String titulo;
    private String encabezado;
    private int estatus;
    private String descripcion;
    private String duracion_h;
    private String duracion_m;
    private String fecha;
    private String src;
    private String imagen_video_preview;
    private int vistas;
    private int estrellas;
    private int landing_visible;
    private boolean visto = false;

    private int opc_visto = 0;

    public Video() {
    }

    public Video(int idvideos, String titulo, String encabezado, int estatus, String descripcion, String duracion_h, String duracion_m, String fecha, String src, String imagen_video_preview, int vistas, int estrellas, int landing_visible) {
        this.idvideos = idvideos;
        this.titulo = titulo;
        this.encabezado = encabezado;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.duracion_h = duracion_h;
        this.duracion_m = duracion_m;
        this.fecha = fecha;
        this.src = src;
        this.imagen_video_preview = imagen_video_preview;
        this.vistas = vistas;
        this.estrellas = estrellas;
        this.landing_visible = landing_visible;
        this.visto = false;
    }

    public Video(int idvideos, String titulo, String encabezado, int estatus, String descripcion, String duracion_h, String duracion_m, String fecha, String src, String imagen_video_preview, int vistas, int estrellas, int landing_visible, int iVisto) {
        this.idvideos = idvideos;
        this.titulo = titulo;
        this.encabezado = encabezado;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.duracion_h = duracion_h;
        this.duracion_m = duracion_m;
        this.fecha = fecha;
        this.src = src;
        this.imagen_video_preview = imagen_video_preview;
        this.vistas = vistas;
        this.estrellas = estrellas;
        this.landing_visible = landing_visible;

        this.opc_visto = iVisto;

        this.visto = false;
    }

    public int getOpc_visto() {
        return opc_visto;
    }

    public void setOpc_visto(int opc_visto) {
        this.opc_visto = opc_visto;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public int getIdvideos() {
        return idvideos;
    }

    public void setIdvideos(int idvideos) {
        this.idvideos = idvideos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDuracion_h() {
        return duracion_h;
    }

    public void setDuracion_h(String duracion_h) {
        this.duracion_h = duracion_h;
    }

    public String getDuracion_m() {
        return duracion_m;
    }

    public void setDuracion_m(String duracion_m) {
        this.duracion_m = duracion_m;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getImagen_video_preview() {
        return imagen_video_preview;
    }

    public void setImagen_video_preview(String imagen_video_preview) {
        this.imagen_video_preview = imagen_video_preview;
    }

    public int getVistas() {
        return vistas;
    }

    public void setVistas(int vistas) {
        this.vistas = vistas;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public int getLanding_visible() {
        return landing_visible;
    }

    public void setLanding_visible(int landing_visible) {
        this.landing_visible = landing_visible;
    }
}

