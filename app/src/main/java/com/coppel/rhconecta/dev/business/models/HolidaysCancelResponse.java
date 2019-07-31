package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidaysCancelResponse extends HolidaysBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable{

        private List<Response> response;

        public List<Response> getResponse() {
            return response;
        }

        public void setResponse(List<Response> response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {

        private int clv_estado;
        private String des_mensaje;

        public int getClv_estado() {
            return clv_estado;
        }

        public void setClv_estado(int clv_estado) {
            this.clv_estado = clv_estado;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }
    }
}
