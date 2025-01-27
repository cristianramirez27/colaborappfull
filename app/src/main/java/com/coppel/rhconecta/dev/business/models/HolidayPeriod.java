package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidayPeriod implements Serializable {
    private int idu_estatus;
    private String nom_estatus;
    private String nom_estaus;
    private String color;
    private String num_dias;
    private String fec_ini="";
    private String fec_fin="";
    private int idu_marca;
    private String color_marca;
    private List<MarkHoliday> ver_marca;
    private int idu_folio;

    private String colorletra;
    private String num_empleado;
    private String nom_empleado;
    private String fotoperfil;


    private String des_comentario;

    private boolean isSelected;
private String idPeriod;

private boolean showMarker;

    public HolidayPeriod() {
    }

    public HolidayPeriod(int idu_estatus, String nom_estatus, String color, String num_dias, String fec_ini, String fec_fin, int idu_folio) {
        this.idu_estatus = idu_estatus;
        this.nom_estatus = nom_estatus;
        this.color = color;
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
        this.idu_folio = idu_folio;
    }

    public HolidayPeriod(int idu_estatus, String num_dias, String fec_ini, String fec_fin) {
        this.idu_estatus = idu_estatus;
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
    }

    public HolidayPeriod(String idPeriod, String num_dias, String fec_ini, String fec_fin) {
        this.idPeriod = idPeriod;
        this.num_dias = num_dias;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
    }

    public int getIdu_estatus() {
        return idu_estatus;
    }

    public void setIdu_estatus(int idu_estatus) {
        this.idu_estatus = idu_estatus;
    }

    public String getNom_estatus() {
        return nom_estatus;
    }

    public void setNom_estatus(String nom_estatus) {
        this.nom_estatus = nom_estatus;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(String num_dias) {
        this.num_dias = num_dias;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public int getIdu_folio() {
        return idu_folio;
    }

    public void setIdu_folio(int idu_folio) {
        this.idu_folio = idu_folio;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIdPeriod() {
        return idPeriod;
    }

    public void setIdPeriod(String idPeriod) {
        this.idPeriod = idPeriod;
    }

    public int getIdu_marca() {
        return idu_marca;
    }

    public void setIdu_marca(int idu_marca) {
        this.idu_marca = idu_marca;
    }

    public String getColor_marca() {
        return color_marca;
    }

    public void setColor_marca(String color_marca) {
        this.color_marca = color_marca;
    }

    public List<MarkHoliday> getVer_marca() {
        return ver_marca;
    }

    public void setVer_marca(List<MarkHoliday> ver_marca) {
        this.ver_marca = ver_marca;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public String getNom_empleado() {
        return nom_empleado;
    }

    public void setNom_empleado(String nom_empleado) {
        this.nom_empleado = nom_empleado;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }


    public boolean isShowMarker() {
        return showMarker;
    }

    public void setShowMarker(boolean showMarker) {
        this.showMarker = showMarker;
    }

    public String getNom_estaus() {
        return nom_estaus;
    }

    public void setNom_estaus(String nom_estaus) {
        this.nom_estaus = nom_estaus;
    }


    public String getColorletra() {
        return colorletra;
    }

    public void setColorletra(String colorletra) {
        this.colorletra = colorletra;
    }

    public String getDes_comentario() {
        return des_comentario;
    }

    public void setDes_comentario(String des_comentario) {
        this.des_comentario = des_comentario;
    }
}