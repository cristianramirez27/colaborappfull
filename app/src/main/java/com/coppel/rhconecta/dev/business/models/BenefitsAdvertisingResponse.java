package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class BenefitsAdvertisingResponse extends BenefitsBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<Advertising> response;

        public List<Advertising> getResponse() {
            return response;
        }

        public void setResponse(List<Advertising> response) {
            this.response = response;
        }
    }
    
    public class Advertising{
        private int id;
        private String descuento;
        private String direccion;
        private String nota;
        private String ruta;
        private String telefono;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescuento() {
            return descuento;
        }

        public void setDescuento(String descuento) {
            this.descuento = descuento;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getNota() {
            return nota;
        }

        public void setNota(String nota) {
            this.nota = nota;
        }

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
    }
}
