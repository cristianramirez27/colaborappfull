package com.coppel.rhconecta.dev.business.models;

public class RolExpensesResponse extends ExpensesTravelBaseResponse {
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
        private int clv_estatus;
        private String des_mensaje;


        public int getClv_estatus() {
            return clv_estatus;
        }

        public void setClv_estatus(int clv_estatus) {
            this.clv_estatus = clv_estatus;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }
    }
}
