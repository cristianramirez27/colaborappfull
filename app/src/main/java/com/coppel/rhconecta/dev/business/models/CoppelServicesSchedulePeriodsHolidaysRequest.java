package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CoppelServicesSchedulePeriodsHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int num_gerente;
    private String des_observaciones;
    private List<HolidayPeriodData> periodos;

    public CoppelServicesSchedulePeriodsHolidaysRequest() {
    }

    public CoppelServicesSchedulePeriodsHolidaysRequest(String num_empleado, int opcion) {
        super(num_empleado, opcion);
    }

    public CoppelServicesSchedulePeriodsHolidaysRequest(int num_gerente, String des_observaciones, List<HolidayPeriodData> periodos) {
        this.num_gerente = num_gerente;
        this.des_observaciones = des_observaciones;
        this.periodos = periodos;
    }

    public CoppelServicesSchedulePeriodsHolidaysRequest(int opcion, int num_gerente, String des_observaciones, List<HolidayPeriodData> periodos) {
        super(opcion);
        this.num_gerente = num_gerente;
        this.des_observaciones = des_observaciones;
        this.periodos = periodos;
    }

    public CoppelServicesSchedulePeriodsHolidaysRequest(Object num_empleado, int opcion, int num_gerente, String des_observaciones, List<HolidayPeriodData> periodos) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.des_observaciones = des_observaciones;
        this.periodos = periodos;
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public String getDes_observaciones() {
        return des_observaciones;
    }

    public void setDes_observaciones(String des_observaciones) {
        this.des_observaciones = des_observaciones;
    }

    public List<HolidayPeriodData> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<HolidayPeriodData> periodos) {
        this.periodos = periodos;
    }
}
