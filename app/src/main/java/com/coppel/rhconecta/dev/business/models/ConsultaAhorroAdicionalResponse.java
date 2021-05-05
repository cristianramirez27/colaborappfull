package com.coppel.rhconecta.dev.business.models;

public class ConsultaAhorroAdicionalResponse extends WithDrawSavingBaseResponse {
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
        private Float imp_cuotaahorro;
        private Float imp_cuotaproceso;
        private Float imp_maximo;
        private String des_titulo;
        private String des_actual;
        private String des_proceso;
        private String des_ahorro;
        private String des_cuota;

        public Float getImp_cuotaahorro() {
            return imp_cuotaahorro;
        }

        public void setImp_cuotaahorro(Float imp_cuotaahorro) {
            this.imp_cuotaahorro = imp_cuotaahorro;
        }

        public Float getImp_cuotaproceso() {
            return imp_cuotaproceso;
        }

        public void setImp_cuotaproceso(Float imp_cuotaproceso) {
            this.imp_cuotaproceso = imp_cuotaproceso;
        }

        public Float getImp_maximo() {
            return imp_maximo;
        }

        public void setImp_maximo(Float imp_maximo) {
            this.imp_maximo = imp_maximo;
        }

        public String getDes_titulo() {
            return des_titulo;
        }

        public void setDes_titulo(String des_titulo) {
            this.des_titulo = des_titulo;
        }

        public String getDes_actual() {
            return des_actual;
        }

        public void setDes_actual(String des_actual) {
            this.des_actual = des_actual;
        }

        public String getDes_proceso() {
            return des_proceso;
        }

        public void setDes_proceso(String des_proceso) {
            this.des_proceso = des_proceso;
        }

        public String getDes_ahorro() {
            return des_ahorro;
        }

        public void setDes_ahorro(String des_ahorro) {
            this.des_ahorro = des_ahorro;
        }

        public String getDes_cuota() {
            return des_cuota;
        }

        public void setDes_cuota(String des_cuota) {
            this.des_cuota = des_cuota;
        }
    }
}
