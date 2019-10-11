package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetPeriodsHolidaysColaboratorsRequest extends CoppelServicesBaseHolidaysRequest {

    private String num_gerente;
    private String num_empconsulta;
    private String fec_ini;
    private String fec_fin;

    private int num_mes;
    private int num_anio;
    private int tipo_consulta;

    public CoppelServicesGetPeriodsHolidaysColaboratorsRequest(int opcion, String num_gerente, String num_empconsulta, String fec_ini, String fec_fin) {
        super(opcion);
        this.num_gerente = num_gerente;
        this.num_empconsulta = num_empconsulta;
        this.fec_ini = fec_ini;
        this.fec_fin = fec_fin;
    }

    public CoppelServicesGetPeriodsHolidaysColaboratorsRequest() {
    }

    public CoppelServicesGetPeriodsHolidaysColaboratorsRequest(String num_gerente) {
        this.num_gerente = num_gerente;
    }

    public CoppelServicesGetPeriodsHolidaysColaboratorsRequest(int opcion, String num_gerente) {
        super( opcion);
        this.num_gerente = num_gerente;
    }


    public String getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(String num_gerente) {
        this.num_gerente = num_gerente;
    }

    public String getNum_empconsulta() {
        return num_empconsulta;
    }

    public void setNum_empconsulta(String num_empconsulta) {
        this.num_empconsulta = num_empconsulta;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public int getNum_mes() {
        return num_mes;
    }

    public void setNum_mes(int num_mes) {
        this.num_mes = num_mes;
    }

    public int getNum_anio() {
        return num_anio;
    }

    public void setNum_anio(int num_anio) {
        this.num_anio = num_anio;
    }

    public int getTipo_consulta() {
        return tipo_consulta;
    }

    public void setTipo_consulta(int tipo_consulta) {
        this.tipo_consulta = tipo_consulta;
    }
}
