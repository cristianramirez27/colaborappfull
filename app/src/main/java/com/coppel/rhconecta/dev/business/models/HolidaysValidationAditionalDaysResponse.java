package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidaysValidationAditionalDaysResponse extends HolidaysBaseResponse {
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

        private int nun_diasadicionales;
        private String des_mensaje;

        public int getNun_diasadicionales() {
            return nun_diasadicionales;
        }

        public void setNun_diasadicionales(int nun_diasadicionales) {
            this.nun_diasadicionales = nun_diasadicionales;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }
    }
}
