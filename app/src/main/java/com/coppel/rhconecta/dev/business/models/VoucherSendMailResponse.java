package com.coppel.rhconecta.dev.business.models;

public class VoucherSendMailResponse extends CoppelGeneralParameterResponse {
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
