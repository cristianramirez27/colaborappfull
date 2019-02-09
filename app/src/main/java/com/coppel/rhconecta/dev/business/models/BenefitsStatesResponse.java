package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class BenefitsStatesResponse extends BenefitsBaseResponse {
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

        private List<States> Estados;

        public List<States> getEstados() {
            return Estados;
        }

        public void setEstados(List<States> estados) {
            Estados = estados;
        }
    }

    public class States extends LocationEntity implements Serializable {
        private int id_es;



        public int getId_es() {
            return id_es;
        }

        public void setId_es(int id_es) {
            this.id_es = id_es;
        }

    }

}
