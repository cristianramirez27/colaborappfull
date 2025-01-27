package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesSendAditionalDaysHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;
    private int num_adicionales;
    private int idu_motivo;
    private String des_otromotivo;

    public CoppelServicesSendAditionalDaysHolidaysRequest(int num_gerente, int num_adicionales, int idu_motivo, String des_otromotivo) {
        this.num_gerente = num_gerente;
        this.num_adicionales = num_adicionales;
        this.idu_motivo = idu_motivo;
        this.des_otromotivo = des_otromotivo;
    }

    public CoppelServicesSendAditionalDaysHolidaysRequest(int opcion, int num_gerente, int num_adicionales, int idu_motivo, String des_otromotivo) {
        super(opcion);
        this.num_gerente = num_gerente;
        this.num_adicionales = num_adicionales;
        this.idu_motivo = idu_motivo;
        this.des_otromotivo = des_otromotivo;
    }

    public CoppelServicesSendAditionalDaysHolidaysRequest(Object num_empleado, int opcion, int num_gerente, int num_adicionales, int idu_motivo, String des_otromotivo) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.num_adicionales = num_adicionales;
        this.idu_motivo = idu_motivo;
        this.des_otromotivo = des_otromotivo;
    }
}
