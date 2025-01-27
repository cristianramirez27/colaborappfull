package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidaysCalendarPeriodsResponse extends HolidaysBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable{

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {

        private List<HolidayPeriod> periodos;
        private int clv_mensaje;
        private String des_mensaje;
        private String des_marca;
        private String des_nota;

        public List<HolidayPeriod> getPeriodos() {
            return periodos;
        }

        public void setPeriodos(List<HolidayPeriod> periodos) {
            this.periodos = periodos;
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

        public String getDes_marca() {
            return des_marca;
        }

        public void setDes_marca(String des_marca) {
            this.des_marca = des_marca;
        }

        public String getDes_nota() {
            return des_nota;
        }

        public void setDes_nota(String des_nota) {
            this.des_nota = des_nota;
        }

    }
}
