package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CoppelServicesSendPeriodsHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;
    private int num_suplente;
    private List<HolidayPeriodData> periodos;

    public CoppelServicesSendPeriodsHolidaysRequest() {
    }

    public CoppelServicesSendPeriodsHolidaysRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesSendPeriodsHolidaysRequest(int num_gerente, int num_suplente, List<HolidayPeriodData> periodos) {
        this.num_gerente = num_gerente;
        this.num_suplente = num_suplente;
        this.periodos = periodos;
    }

    public CoppelServicesSendPeriodsHolidaysRequest(Object num_empleado, int opcion, int num_gerente, int num_suplente, List<HolidayPeriodData> periodos) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.num_suplente = num_suplente;
        this.periodos = periodos;
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public int getNum_suplente() {
        return num_suplente;
    }

    public void setNum_suplente(int num_suplente) {
        this.num_suplente = num_suplente;
    }

    public List<HolidayPeriodData> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<HolidayPeriodData> periodos) {
        this.periodos = periodos;
    }
}
