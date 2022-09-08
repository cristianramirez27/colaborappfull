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

    public class Data implements Serializable {

        private Response[] response;

        public Response[] getResponse() {
            return response;
        }

        public void setResponse(Response[] response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        /**
         * Help desk chat service message
         */
        private String des_msj;
        /**
         * Date on which the service will be consumed again
         * Format: 2022-09-01
         */
        private String fecha;
        /**
         * Date with the start or end time of the message
         * Format: 2022-09-01 19:00:00
         */
        private String fechaHora;
        /**
         * Remaining time to consume the service again
         * Format: 00:44:52
         */
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
