package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class BenefitsSearchEmptyResponse extends BenefitsSearchResponse {
    public Data data;

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

        private String menssage ;

        private List<BenefitsCategoriesResponse.CategoryEmpty> Categorias;

        public List<BenefitsCategoriesResponse.CategoryEmpty> getCategorias() {
            return Categorias;
        }

        public void setCategorias(List<BenefitsCategoriesResponse.CategoryEmpty> categorias) {
            Categorias = categorias;
        }
    }

}
