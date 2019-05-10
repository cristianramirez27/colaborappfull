package com.coppel.rhconecta.dev.business.models;

public class LogoutResponse extends CoppelGeneralParameterResponse {

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
        private String Msg;
        private int estado;

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String msg) {
            Msg = msg;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }
    }

}
