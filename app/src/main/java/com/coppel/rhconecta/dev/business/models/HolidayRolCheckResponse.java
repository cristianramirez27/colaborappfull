package com.coppel.rhconecta.dev.business.models;

public class HolidayRolCheckResponse extends HolidaysBaseResponse {
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
        private int gerente;
        private int suplente;

        public int getGerente() {
            return gerente;
        }

        public void setGerente(int gerente) {
            this.gerente = gerente;
        }

        public int getSuplente() {
            return suplente;
        }

        public void setSuplente(int suplente) {
            this.suplente = suplente;
        }
    }
}
