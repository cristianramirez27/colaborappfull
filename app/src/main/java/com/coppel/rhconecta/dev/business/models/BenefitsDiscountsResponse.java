package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class BenefitsDiscountsResponse extends BenefitsBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<Discount> response;

        public List<Discount> getResponse() {
            return response;
        }

        public void setResponse(List<Discount> response) {
            this.response = response;
        }
    }

    public class Discount implements Serializable {
        private int id_empresa;
        private String descripciondes;
        /*Este parametro se agrega para los resultados de la busqueda ya que el ws regresa el valor de {descripciondes}
        en una nueva variable con este nombre {descripcionapp}*/
        private String descripcionapp;
        private String descuento;
        private String ruta;


        public int getId_empresa() {
            return id_empresa;
        }

        public void setId_empresa(int id_empresa) {
            this.id_empresa = id_empresa;
        }

        public String getDescripciondes() {
            return descripciondes;
        }

        public void setDescripciondes(String descripciondes) {
            this.descripciondes = descripciondes;
        }

        public String getDescuento() {
            return descuento;
        }

        public void setDescuento(String descuento) {
            this.descuento = descuento;
        }

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }

        public String getDescripcionapp() {
            return descripcionapp;
        }

        public void setDescripcionapp(String descripcionapp) {
            this.descripcionapp = descripcionapp;
        }
    }
}
