package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.GuardarEncuesta.Request;

import java.util.ArrayList;

public class JSON_GuardarEncuesta {

    private String numeroempleado;
    private ArrayList<DataForm> dataForm;
    private String aplicacionkey;


    public JSON_GuardarEncuesta() {
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public JSON_GuardarEncuesta(String numeroempleado, ArrayList<DataForm> dataForm) {
        this.numeroempleado = numeroempleado;
        this.dataForm = dataForm;
        this.aplicacionkey = "c2VydmljaW9jb3BwZWx2aWRlbw==";
    }

    public String getNumeroempleado() {
        return numeroempleado;
    }

    public JSON_GuardarEncuesta(String numeroempleado, ArrayList<DataForm> dataForm, String aplicacionkey) {
        this.numeroempleado = numeroempleado;
        this.dataForm = dataForm;
        this.aplicacionkey = aplicacionkey;
    }

    public void setNumeroempleado(String numeroempleado) {
        this.numeroempleado = numeroempleado;
    }

    public ArrayList<DataForm> getDataForm() {
        return dataForm;
    }

    public void setDataForm(ArrayList<DataForm> dataForm) {
        this.dataForm = dataForm;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }
}
