package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class ExternalUrlResponse extends CoppelGeneralParameterResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<Response> response;


        public List<Response> getResponse() {
            return response;
        }

        public void setResponse(List<Response> response) {
            this.response = response;
        }
    }

    public class Response {

        private String clv_urlservicio;
        public int flag;

        public String getClv_urlservicio() {
            return clv_urlservicio;
        }

        public void setClv_urlservicio(String clv_urlservicio) {
            this.clv_urlservicio = clv_urlservicio;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

    }

    public class ExternalUrlData implements Serializable {

    }
}
