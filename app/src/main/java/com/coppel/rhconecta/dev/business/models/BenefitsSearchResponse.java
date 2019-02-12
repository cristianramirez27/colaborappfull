package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class BenefitsSearchResponse extends BenefitsBaseResponse {
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

        private List<BenefitsCategoriesResponse.Category> Categorias;

        public List<BenefitsCategoriesResponse.Category> getCategorias() {
            return Categorias;
        }

        public void setCategorias(List<BenefitsCategoriesResponse.Category> categorias) {
            Categorias = categorias;
        }
    }

}
