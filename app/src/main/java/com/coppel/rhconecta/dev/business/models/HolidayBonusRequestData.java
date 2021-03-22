package com.coppel.rhconecta.dev.business.models;

public class HolidayBonusRequestData  extends  CoppelServicesBaseHolidaysRequest{
    private int clv_opcion;
    private String fec_quincena;

    public HolidayBonusRequestData(String num_empleado, int clv_opcion) {
        setNum_empleado(num_empleado);
        this.clv_opcion = clv_opcion;
    }
    public HolidayBonusRequestData(String num_empleado, int clv_opcion, String fec_quincena) {
        setNum_empleado(num_empleado);
        this.clv_opcion = clv_opcion;
        this.fec_quincena = fec_quincena;
    }

    public int getClv_opcion() {
        return clv_opcion;
    }

    public void setClv_opcion(int clv_opcion) {
        this.clv_opcion = clv_opcion;
    }

    public String getFec_quincena() {
        return fec_quincena;
    }

    public void setFec_quincena(String fec_quincena) {
        this.fec_quincena = fec_quincena;
    }
}
