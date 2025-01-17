package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class ColaboratorControlsMonthResponse extends ExpensesTravelBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response {
        private List<ControlMonth> Controles;


        public List<ControlMonth> getControles() {
            return Controles;
        }

        public void setControles(List<ControlMonth> controles) {
            Controles = controles;
        }
    }

    public class ControlMonth implements Serializable {
        private int numviajero;
        private String nomviajero;
        private String control;
        private int estado;
        private int clv_estatus;
        private String estatus = "";
        private String fec_regreso;
        private String fec_salida;
        private String itinerario;
        private String razon_viaje;
        private String des_color;
        private String des_colorletra;

        public String getDes_colorletra() {
            return des_colorletra;
        }

        public void setDes_colorletra(String des_colorletra) {
            this.des_colorletra = des_colorletra;
        }

        public String getDes_color() {
            return des_color;
        }

        public void setDes_color(String des_color) {
            this.des_color = des_color;
        }

        public int getNumviajero() {
            return numviajero;
        }

        public void setNumviajero(int numviajero) {
            this.numviajero = numviajero;
        }

        public String getNomviajero() {
            return nomviajero;
        }

        public void setNomviajero(String nomviajero) {
            this.nomviajero = nomviajero;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public int getClv_estatus() {
            return clv_estatus;
        }

        public void setClv_estatus(int clv_estatus) {
            this.clv_estatus = clv_estatus;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getFec_regreso() {
            return fec_regreso;
        }

        public void setFec_regreso(String fec_regreso) {
            this.fec_regreso = fec_regreso;
        }

        public String getFec_salida() {
            return fec_salida;
        }

        public void setFec_salida(String fec_salida) {
            this.fec_salida = fec_salida;
        }

        public String getItinerario() {
            return itinerario;
        }

        public void setItinerario(String itinerario) {
            this.itinerario = itinerario;
        }

        public String getRazon_viaje() {
            return razon_viaje;
        }

        public void setRazon_viaje(String razon_viaje) {
            this.razon_viaje = razon_viaje;
        }
    }

}
