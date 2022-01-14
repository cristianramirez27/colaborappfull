package com.coppel.rhconecta.dev.business.models;

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
        private Float imp_margencredito = 0f;
        private Float imp_ahorroadicional = 0f;
        private Float imp_total = 0f;
        private String des_proceso;
        private String des_cambiar;
        private String des_mensaje;
        private int opc_retiroenlinea = 0;


        public Float getImp_margencredito() {
            return imp_margencredito;
        }

        public void setImp_margencredito(Float imp_margencredito) {
            this.imp_margencredito = imp_margencredito;
        }

        public Float getImp_ahorroadicional() {
            return imp_ahorroadicional;
        }

        public void setImp_ahorroadicional(Float imp_ahorroadicional) {
            this.imp_ahorroadicional = imp_ahorroadicional;
        }

        public Float getImp_total() {
            return imp_total;
        }

        public void setImp_total(Float imp_total) {
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

        public int getOpc_retiroenlinea() {
            return opc_retiroenlinea;
        }

        public void setOpc_retiroenlinea(int opc_retiroenlinea) {
            this.opc_retiroenlinea = opc_retiroenlinea;
        }
    }
}
