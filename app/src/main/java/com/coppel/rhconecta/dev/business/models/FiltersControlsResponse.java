package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class FiltersControlsResponse extends ExpensesTravelBaseResponse {
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
        private List<Center> Centros;
        private List<Estatus> Estatus;

        public List<Center> getCentros() {
            return Centros;
        }

        public void setCentros(List<Center> centros) {
            Centros = centros;
        }

        public List<com.coppel.rhconecta.dev.business.models.Estatus> getEstatus() {
            return Estatus;
        }

        public void setEstatus(List<com.coppel.rhconecta.dev.business.models.Estatus> estatus) {
            Estatus = estatus;
        }
    }


}
