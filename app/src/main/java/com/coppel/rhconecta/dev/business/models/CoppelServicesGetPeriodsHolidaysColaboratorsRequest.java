package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesGetPeriodsHolidaysColaboratorsRequest extends CoppelServicesBaseHolidaysRequest {

    private String num_gerente;
    private String num_empconsulta;
    private String fec_ini;
    private String fec_fin;

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


}
