package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Request;

public class DataForm {

    private int value;
    private String id;

    public DataForm(int value, String id) {
        this.value = value;
        this.id = id;
    }

    public DataForm() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int valude) {
        this.value = valude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
