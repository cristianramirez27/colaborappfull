package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class BenefitsEmptyResponse extends BenefitsBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private String response;


        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

}
