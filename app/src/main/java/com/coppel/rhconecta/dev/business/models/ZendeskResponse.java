package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class ZendeskResponse extends CoppelGeneralParameterResponse implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{

        private Response[] response;

        public Response[] getResponse() {
            return response;
        }

        public void setResponse(Response[] response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        private String des_msj;
        private String fecha;
        private String fechaHora;
        private String horas;

        public Response() {
        }


        public String getDes_msj() {
            return des_msj;
        }

        public void setDes_msj(String des_msj) {
            this.des_msj = des_msj;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(String fechaHora) {
            this.fechaHora = fechaHora;
        }

        public String getHoras() {
            return horas;
        }

        public void setHoras(String horas) {
            this.horas = horas;
        }
    }

}
