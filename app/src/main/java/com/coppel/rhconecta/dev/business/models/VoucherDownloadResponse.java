package com.coppel.rhconecta.dev.business.models;

public class VoucherDownloadResponse extends CoppelGeneralParameterResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private Pdf response;

        public Pdf getResponse() {
            return response;
        }

        public void setResponse(Pdf response) {
            this.response = response;
        }
    }

    public class Pdf {

        private String pdf;

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

    }
}
