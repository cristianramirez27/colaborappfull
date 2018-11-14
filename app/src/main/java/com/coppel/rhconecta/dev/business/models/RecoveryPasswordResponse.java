package com.coppel.rhconecta.dev.business.models;

public class RecoveryPasswordResponse extends CoppelGeneralParameterResponse {
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

        private String clv_urlservicio;
        private String flag;

        public String getClv_urlservicio() {
            return clv_urlservicio;
        }

        public void setClv_urlservicio(String clv_urlservicio) {
            this.clv_urlservicio = clv_urlservicio;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

    }
}
