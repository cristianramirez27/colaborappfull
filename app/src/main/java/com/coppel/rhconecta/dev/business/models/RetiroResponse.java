package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class RetiroResponse extends WithDrawSavingBaseResponse {
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
        private int imp_margencredito;
        private int imp_ahorroadicional;
        private int imp_total;
        private String des_proceso;
        private String des_cambiar;
        private String des_mensaje;


        public int getImp_margencredito() {
            return imp_margencredito;
        }

        public void setImp_margencredito(int imp_margencredito) {
            this.imp_margencredito = imp_margencredito;
        }

        public int getImp_ahorroadicional() {
            return imp_ahorroadicional;
        }

        public void setImp_ahorroadicional(int imp_ahorroadicional) {
            this.imp_ahorroadicional = imp_ahorroadicional;
        }

        public int getImp_total() {
            return imp_total;
        }

        public void setImp_total(int imp_total) {
            this.imp_total = imp_total;
        }

        public String getDes_proceso() {
            return des_proceso;
        }

        public void setDes_proceso(String des_proceso) {
            this.des_proceso = des_proceso;
        }

        public String getDes_cambiar() {
            return des_cambiar;
        }

        public void setDes_cambiar(String des_cambiar) {
            this.des_cambiar = des_cambiar;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }
    }
}
