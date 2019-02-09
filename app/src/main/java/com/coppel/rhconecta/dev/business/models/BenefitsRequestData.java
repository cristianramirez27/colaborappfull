package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.BenefitsType;

import java.io.Serializable;

public class BenefitsRequestData implements Serializable{

    private BenefitsType benefits_type;
    private int solicitud;
    private String num_estado;
    private String num_ciudad;
    private String clave_servicio;
    private int idempresa;
    private String des_busqueda;

    public BenefitsRequestData(BenefitsType benefits_type, int solicitud) {
        this.benefits_type = benefits_type;
        this.solicitud = solicitud;
    }

    public BenefitsRequestData(BenefitsType benefits_type, int solicitud, String num_estado) {
        this.benefits_type = benefits_type;
        this.solicitud = solicitud;
        this.num_estado = num_estado;
    }

    public BenefitsRequestData(BenefitsType benefits_type,int solicitud, String num_estado, String num_ciudad) {
        this.benefits_type = benefits_type;
        this.solicitud = solicitud;
        this.num_estado = num_estado;
        this.num_ciudad = num_ciudad;
    }

    public BenefitsRequestData(BenefitsType benefits_type,int solicitud, String num_estado, String num_ciudad, String clave_servicio) {
        this.benefits_type = benefits_type;
        this.solicitud = solicitud;
        this.num_estado = num_estado;
        this.num_ciudad = num_ciudad;
        this.clave_servicio = clave_servicio;
    }

    public BenefitsRequestData(BenefitsType benefits_type,int solicitud, String num_estado, String num_ciudad, String clave_servicio, int idempresa) {
        this.benefits_type = benefits_type;
        this.solicitud = solicitud;
        this.num_estado = num_estado;
        this.num_ciudad = num_ciudad;
        this.clave_servicio = clave_servicio;
        this.idempresa = idempresa;
    }


    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public String getNum_estado() {
        return num_estado;
    }

    public void setNum_estado(String num_estado) {
        this.num_estado = num_estado;
    }

    public String getNum_ciudad() {
        return num_ciudad;
    }

    public void setNum_ciudad(String num_ciudad) {
        this.num_ciudad = num_ciudad;
    }

    public String getClave_servicio() {
        return clave_servicio;
    }

    public void setClave_servicio(String clave_servicio) {
        this.clave_servicio = clave_servicio;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getDes_busqueda() {
        return des_busqueda;
    }

    public void setDes_busqueda(String des_busqueda) {
        this.des_busqueda = des_busqueda;
    }

    public BenefitsType getBenefits_type() {
        return benefits_type;
    }

    public void setBenefits_type(BenefitsType benefits_type) {
        this.benefits_type = benefits_type;
    }
}
