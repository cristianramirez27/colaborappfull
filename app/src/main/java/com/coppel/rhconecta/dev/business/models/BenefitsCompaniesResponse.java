package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class BenefitsCompaniesResponse extends BenefitsBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<Company> response;

        public List<Company> getResponse() {
            return response;
        }

        public void setResponse(List<Company> response) {
            this.response = response;
        }
    }
    
    public class Company implements Serializable {
        private int activo;
        private String aplica;
        private int cve;
        private String des_descripcionapp;
        private String descuento;
        private String domicilio;
        private String empresa;
        private String fecha_fin;
        private String fecha_ini;
        private String id_empresa;
        private String nota;
        private String requisitos;
        private String ruta;
        private int servicios;
        private String telefono;
        private int vigencia;


        public int getActivo() {
            return activo;
        }

        public void setActivo(int activo) {
            this.activo = activo;
        }

        public String getAplica() {
            return aplica;
        }

        public void setAplica(String aplica) {
            this.aplica = aplica;
        }

        public int getCve() {
            return cve;
        }

        public void setCve(int cve) {
            this.cve = cve;
        }

        public String getDes_descripcionapp() {
            return des_descripcionapp;
        }

        public void setDes_descripcionapp(String des_descripcionapp) {
            this.des_descripcionapp = des_descripcionapp;
        }

        public String getDescuento() {
            return descuento;
        }

        public void setDescuento(String descuento) {
            this.descuento = descuento;
        }

        public String getDomicilio() {
            return domicilio;
        }

        public void setDomicilio(String domicilio) {
            this.domicilio = domicilio;
        }

        public String getEmpresa() {
            return empresa;
        }

        public void setEmpresa(String empresa) {
            this.empresa = empresa;
        }

        public String getFecha_fin() {
            return fecha_fin;
        }

        public void setFecha_fin(String fecha_fin) {
            this.fecha_fin = fecha_fin;
        }

        public String getFecha_ini() {
            return fecha_ini;
        }

        public void setFecha_ini(String fecha_ini) {
            this.fecha_ini = fecha_ini;
        }

        public String getId_empresa() {
            return id_empresa;
        }

        public void setId_empresa(String id_empresa) {
            this.id_empresa = id_empresa;
        }

        public String getNota() {
            return nota;
        }

        public void setNota(String nota) {
            this.nota = nota;
        }

        public String getRequisitos() {
            return requisitos;
        }

        public void setRequisitos(String requisitos) {
            this.requisitos = requisitos;
        }

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }

        public int getServicios() {
            return servicios;
        }

        public void setServicios(int servicios) {
            this.servicios = servicios;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public int getVigencia() {
            return vigencia;
        }

        public void setVigencia(int vigencia) {
            this.vigencia = vigencia;
        }
    }
}
