package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HolidayBonusPeriodResponse extends HolidaysBaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        private int clv_estado;
        private String des_mensaje;
        private List<PeriodDate> fechas_primas;

        public int getClv_estado() {
            return clv_estado;
        }

        public void setClv_estado(int clv_estado) {
            this.clv_estado = clv_estado;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }

        public List<PeriodDate> getFechas_primas() {
            return fechas_primas;
        }

        public void setFechas_primas(List<PeriodDate> fechas_primas) {
            this.fechas_primas = fechas_primas;
        }

        public class PeriodDate {
           private String nom_mes;
           private String fec_diames1;
           private String fec_diames2;
           private String fec_quincena1;
           private String fec_quincena2;

           public String getNom_mes() {
               return nom_mes;
           }

           public void setNom_mes(String nom_mes) {
               this.nom_mes = nom_mes;
           }

           public String getFec_diames1() {
               return fec_diames1;
           }

           public void setFec_diames1(String fec_diames1) {
               this.fec_diames1 = fec_diames1;
           }

           public String getFec_diames2() {
               return fec_diames2;
           }

           public void setFec_diames2(String fec_diames2) {
               this.fec_diames2 = fec_diames2;
           }

           public String getFec_quincena1() {
               return fec_quincena1;
           }

           public void setFec_quincena1(String fec_quincena1) {
               this.fec_quincena1 = fec_quincena1;
           }

           public String getFec_quincena2() {
               return fec_quincena2;
           }

           public void setFec_quincena2(String fec_quincena2) {
               this.fec_quincena2 = fec_quincena2;
           }

           public List<DateCalendar> toMapDateCalendarList(){
               List<DateCalendar> result = new ArrayList<>();
               DateCalendar date = new DateCalendar(fec_quincena1.substring(0,2),fec_diames1.replace(",",",\n"),fec_quincena1);
               result.add(date);
               if (fec_diames2 != null && !fec_diames2.isEmpty() && !fec_quincena2.isEmpty()) {
                   DateCalendar date2 = new DateCalendar(fec_quincena2.substring(0, 2), fec_diames2.replace(",", ",\n"), fec_quincena2);
                   result.add(date2);
               }
               return result;
           }
       }
    }
}
