package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class BenefitsSearchResultsResponse extends BenefitsSearchResponse {
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

        private List<BenefitsCategoriesResponse.CategoryResult> Categorias;

        public List<BenefitsCategoriesResponse.CategoryResult> getCategorias() {
            return Categorias;
        }

        public void setCategorias(List<BenefitsCategoriesResponse.CategoryResult> categorias) {
            Categorias = categorias;
        }
    }

}
