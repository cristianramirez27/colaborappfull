package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class CoppelGeneralParameterResponse implements Serializable{

    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public class Meta {

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
