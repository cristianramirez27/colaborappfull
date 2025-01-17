package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class CoppelServicesChangeStatusHolidaysRequest extends CoppelServicesBaseHolidaysRequest {

    private int idu_autorizo;
    private int num_gerente;
    private String des_comentario;
    private List<HolidayPeriodFolio> periodos;

    public CoppelServicesChangeStatusHolidaysRequest(Object num_empleado, int opcion, int idu_autorizo, int num_gerente, String des_comentario, List<HolidayPeriodFolio> periodos) {
        super(num_empleado, opcion);
        this.idu_autorizo = idu_autorizo;
        this.num_gerente = num_gerente;
        this.des_comentario = des_comentario;
        this.periodos = periodos;
    }

    public CoppelServicesChangeStatusHolidaysRequest(Object num_empleado, int opcion, int num_gerente, String des_comentario, List<HolidayPeriodFolio> periodos) {
        super(num_empleado, opcion);
        this.num_gerente = num_gerente;
        this.des_comentario = des_comentario;
        this.periodos = periodos;
    }

    public int getIdu_autorizo() {
        return idu_autorizo;
    }

    public void setIdu_autorizo(int idu_autorizo) {
        this.idu_autorizo = idu_autorizo;
    }

    public int getNum_gerente() {
        return num_gerente;
    }

    public void setNum_gerente(int num_gerente) {
        this.num_gerente = num_gerente;
    }

    public String getDes_comentario() {
        return des_comentario;
    }

    public void setDes_comentario(String des_comentario) {
        this.des_comentario = des_comentario;
    }

    public List<HolidayPeriodFolio> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<HolidayPeriodFolio> periodos) {
        this.periodos = periodos;
    }
}
