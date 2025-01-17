package com.coppel.rhconecta.dev.business.models;

public class HolidayBonusRequestData  extends  CoppelServicesBaseHolidaysRequest{
    private String fec_quincena;

    public HolidayBonusRequestData(int num_empleado, int clv_opcion) {
        //setNum_empleado(num_empleado);
        super(num_empleado,clv_opcion);
        //this.clv_opcion = clv_opcion;
    }
    public HolidayBonusRequestData(int num_empleado, int clv_opcion, String fec_quincena) {
        super(num_empleado,clv_opcion);
        this.fec_quincena = fec_quincena;
    }



    public String getFec_quincena() {
        return fec_quincena;
    }

    public void setFec_quincena(String fec_quincena) {
        this.fec_quincena = fec_quincena;
    }
}
