package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class HolidayGetDetailPeriodResponse extends HolidaysBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<Response> response;

        public List<Response> getResponse() {
            return response;
        }

        public void setResponse(List<Response> response) {
            this.response = response;
        }
    }

    public class Response {
        private String fec_ini;
        private String fec_fin;
        private String num_dias;
        private int idu_autorizo;
        private int idu_estatus;
        private String des_estatus;
        private String color;
        private String num_gerente;
        private String nom_gerente;
        private String color_gerente;
        private String fec_estatus;
        private int idu_folio;
        private String des_comentario;


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

        public String getNum_dias() {
            return num_dias;
        }

        public void setNum_dias(String num_dias) {
            this.num_dias = num_dias;
        }

        public int getIdu_autorizo() {
            return idu_autorizo;
        }

        public void setIdu_autorizo(int idu_autorizo) {
            this.idu_autorizo = idu_autorizo;
        }

        public int getIdu_estatus() {
            return idu_estatus;
        }

        public void setIdu_estatus(int idu_estatus) {
            this.idu_estatus = idu_estatus;
        }

        public String getDes_estatus() {
            return des_estatus;
        }

        public void setDes_estatus(String des_estatus) {
            this.des_estatus = des_estatus;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getNum_gerente() {
            return num_gerente;
        }

        public void setNum_gerente(String num_gerente) {
            this.num_gerente = num_gerente;
        }

        public String getNom_gerente() {
            return nom_gerente;
        }

        public void setNom_gerente(String nom_gerente) {
            this.nom_gerente = nom_gerente;
        }

        public String getColor_gerente() {
            return color_gerente;
        }

        public void setColor_gerente(String color_gerente) {
            this.color_gerente = color_gerente;
        }

        public String getFec_estatus() {
            return fec_estatus;
        }

        public void setFec_estatus(String fec_estatus) {
            this.fec_estatus = fec_estatus;
        }

        public int getIdu_folio() {
            return idu_folio;
        }

        public void setIdu_folio(int idu_folio) {
            this.idu_folio = idu_folio;
        }

        public String getDes_comentario() {
            return des_comentario;
        }

        public void setDes_comentario(String des_comentario) {
            this.des_comentario = des_comentario;
        }
    }
}
