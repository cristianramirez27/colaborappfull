package com.coppel.rhconecta.dev.business.models;

public class ConsultaAbonoResponse extends WithDrawSavingBaseResponse {
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
        private float imp_cuentacorriente;
        private int clv_retirocuentacorriente;
        private float imp_ahorroadicional;
        private int clv_retiroahorroadicional;
        private float imp_fondotrabajador;
        private int clv_retirofondotrabajador;
        private String des_proceso;
        private String des_cambiar;
        private String des_mensaje;
        private String des_abonoProceso;

        public float getImp_cuentacorriente() {
            return imp_cuentacorriente;
        }

        public void setImp_cuentacorriente(float imp_cuentacorriente) {
            this.imp_cuentacorriente = imp_cuentacorriente;
        }

        public int getClv_retirocuentacorriente() {
            return clv_retirocuentacorriente;
        }

        public void setClv_retirocuentacorriente(int clv_retirocuentacorriente) {
            this.clv_retirocuentacorriente = clv_retirocuentacorriente;
        }

        public float getImp_ahorroadicional() {
            return imp_ahorroadicional;
        }

        public void setImp_ahorroadicional(float imp_ahorroadicional) {
            this.imp_ahorroadicional = imp_ahorroadicional;
        }

        public int getClv_retiroahorroadicional() {
            return clv_retiroahorroadicional;
        }

        public void setClv_retiroahorroadicional(int clv_retiroahorroadicional) {
            this.clv_retiroahorroadicional = clv_retiroahorroadicional;
        }

        public float getImp_fondotrabajador() {
            return imp_fondotrabajador;
        }

        public void setImp_fondotrabajador(float imp_fondotrabajador) {
            this.imp_fondotrabajador = imp_fondotrabajador;
        }

        public int getClv_retirofondotrabajador() {
            return clv_retirofondotrabajador;
        }

        public void setClv_retirofondotrabajador(int clv_retirofondotrabajador) {
            this.clv_retirofondotrabajador = clv_retirofondotrabajador;
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

        public String getDes_abonoProceso() {
            return des_abonoProceso;
        }

        public void setDes_abonoProceso(String des_abonoProceso) {
            this.des_abonoProceso = des_abonoProceso;
        }
    }
}
