package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CentersHolidayResponse extends HolidaysBaseResponse {
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
        private List<Center> centros;
        private int clv_mensaje;
        private String des_mensaje;

        public List<Center> getCentros() {
            return centros;
        }

        public void setCentros(List<Center> centros) {
            this.centros = centros;
        }

        public int getClv_mensaje() {
            return clv_mensaje;
        }

        public void setClv_mensaje(int clv_mensaje) {
            this.clv_mensaje = clv_mensaje;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }
    }

}
