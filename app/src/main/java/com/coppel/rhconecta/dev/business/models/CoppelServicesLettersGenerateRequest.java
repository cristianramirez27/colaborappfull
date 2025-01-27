package com.coppel.rhconecta.dev.business.models;

import java.util.List;
import java.util.Map;

public class CoppelServicesLettersGenerateRequest<T> {

    private String num_empleado;
    private int tipo_carta;
    private int opcionEnvio;
    private String correo;
    private Map<String, T> datos;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public int getTipo_carta() {
        return tipo_carta;
    }

    public void setTipo_carta(int tipo_carta) {
        this.tipo_carta = tipo_carta;
    }

    public int getOpcionEnvio() {
        return opcionEnvio;
    }

    public void setOpcionEnvio(int opcionEnvio) {
        this.opcionEnvio = opcionEnvio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Map<String, T> getDatos() {
        return datos;
    }

    public void setDatos(Map<String, T> datos) {
        this.datos = datos;
    }


    public static class Data {
        private List<LetterChildrenData> childrenData;
        private LetterScheduleData scheduleData;
        private LetterJobScheduleData jobScheduleData;
        private LetterSectionScheduleData sectionScheduleData;
        private LetterHolidayData letterHolidayData;

        public List<LetterChildrenData> getChildrenData() {
            return childrenData;
        }

        public void setChildrenData(List<LetterChildrenData> childrenData) {
            this.childrenData = childrenData;
        }

        public LetterScheduleData getScheduleData() {
            return scheduleData;
        }

        public void setScheduleData(LetterScheduleData scheduleData) {
            this.scheduleData = scheduleData;
        }

        public LetterJobScheduleData getJobScheduleData() {
            return jobScheduleData;
        }

        public void setJobScheduleData(LetterJobScheduleData jobScheduleData) {
            this.jobScheduleData = jobScheduleData;
        }

        public LetterSectionScheduleData getSectionScheduleData() {
            return sectionScheduleData;
        }

        public void setSectionScheduleData(LetterSectionScheduleData sectionScheduleData) {
            this.sectionScheduleData = sectionScheduleData;
        }

        public LetterHolidayData getLetterHolidayData() {
            return letterHolidayData;
        }

        public void setLetterHolidayData(LetterHolidayData letterHolidayData) {
            this.letterHolidayData = letterHolidayData;
        }
    }

}