package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.DetailExpenseTravelType;

import java.io.Serializable;

public class DetailExpenseTravelData implements Serializable {

        private DetailExpenseTravelType detailExpenseTravelType;
        private int clave;
        private Object data;

        private String observations;

    public DetailExpenseTravelData(DetailExpenseTravelType detailExpenseTravelType, int clave) {
        this.detailExpenseTravelType = detailExpenseTravelType;
        this.clave = clave;
    }

    public DetailExpenseTravelType getDetailExpenseTravelType() {
        return detailExpenseTravelType;
    }

    public void setDetailExpenseTravelType(DetailExpenseTravelType detailExpenseTravelType) {
        this.detailExpenseTravelType = detailExpenseTravelType;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
