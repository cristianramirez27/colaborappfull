package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidaysPeriodsResponse extends HolidaysBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable{

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        private double num_totalvacaciones;
        private double num_diasvacaciones;
        private double num_diasporautorizar;
        private double num_diasautorizados;
        private double num_diasagendados;
        private double num_decision;
        private double num_decisionanterior;
        private double num_adicionales;
        private double num_adicionalesagregadas;
        private int clv_mediodia;
        private String fec_primavacacional;
        private List<HolidayPeriod> periodos;
        private int clv_mensaje;
        private String des_mensaje;
        private String des_marca;
        private String des_config;

        private String des_nota;


        public double getNum_totalvacaciones() {
            return num_totalvacaciones;
        }

        public void setNum_totalvacaciones(int num_totalvacaciones) {
            this.num_totalvacaciones = num_totalvacaciones;
        }

        public double getNum_diasvacaciones() {
            return num_diasvacaciones;
        }

        public void setNum_diasvacaciones(int num_diasvacaciones) {
            this.num_diasvacaciones = num_diasvacaciones;
        }

        public double getNum_diasporautorizar() {
            return num_diasporautorizar;
        }

        public void setNum_diasporautorizar(int num_diasporautorizar) {
            this.num_diasporautorizar = num_diasporautorizar;
        }

        public double getNum_diasautorizados() {
            return num_diasautorizados;
        }

        public void setNum_diasautorizados(int num_diasautorizados) {
            this.num_diasautorizados = num_diasautorizados;
        }

        public double getNum_diasagendados() {
            return num_diasagendados;
        }

        public void setNum_diasagendados(int num_diasagendados) {
            this.num_diasagendados = num_diasagendados;
        }

        public double getNum_decision() {
            return num_decision;
        }

        public void setNum_decision(int num_decision) {
            this.num_decision = num_decision;
        }

        public double getNum_decisionanterior() {
            return num_decisionanterior;
        }

        public void setNum_decisionanterior(int num_decisionanterior) {
            this.num_decisionanterior = num_decisionanterior;
        }

        public double getNum_adicionales() {
            return num_adicionales;
        }

        public void setNum_adicionales(int num_adicionales) {
            this.num_adicionales = num_adicionales;
        }

        public double getNum_adicionalesagregadas() {
            return num_adicionalesagregadas;
        }

        public void setNum_adicionalesagregadas(int num_adicionalesagregadas) {
            this.num_adicionalesagregadas = num_adicionalesagregadas;
        }

        public int getClv_mediodia() {
            return clv_mediodia;
        }

        public void setClv_mediodia(int clv_mediodia) {
            this.clv_mediodia = clv_mediodia;
        }

        public String getFec_primavacacional() {
            return fec_primavacacional;
        }

        public void setFec_primavacacional(String fec_primavacacional) {
            this.fec_primavacacional = fec_primavacacional;
        }

        public List<HolidayPeriod> getPeriodos() {
            return periodos;
        }

        public void setPeriodos(List<HolidayPeriod> periodos) {
            this.periodos = periodos;
        }

        public int getClv_mensaje() {
            return clv_mensaje;
        }

        public void setClv_mensaje(int clv_mensaje) {
            this.clv_mensaje = clv_mensaje;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }

        public String getDes_marca() {
            return des_marca;
        }

        public void setDes_marca(String des_marca) {
            this.des_marca = des_marca;
        }

        public void setNum_totalvacaciones(double num_totalvacaciones) {
            this.num_totalvacaciones = num_totalvacaciones;
        }

        public void setNum_diasvacaciones(double num_diasvacaciones) {
            this.num_diasvacaciones = num_diasvacaciones;
        }

        public void setNum_diasporautorizar(double num_diasporautorizar) {
            this.num_diasporautorizar = num_diasporautorizar;
        }

        public void setNum_diasautorizados(double num_diasautorizados) {
            this.num_diasautorizados = num_diasautorizados;
        }

        public void setNum_diasagendados(double num_diasagendados) {
            this.num_diasagendados = num_diasagendados;
        }

        public void setNum_decision(double num_decision) {
            this.num_decision = num_decision;
        }

        public void setNum_decisionanterior(double num_decisionanterior) {
            this.num_decisionanterior = num_decisionanterior;
        }

        public void setNum_adicionales(double num_adicionales) {
            this.num_adicionales = num_adicionales;
        }

        public void setNum_adicionalesagregadas(double num_adicionalesagregadas) {
            this.num_adicionalesagregadas = num_adicionalesagregadas;
        }

        public String getDes_nota() {
            return des_nota;
        }

        public void setDes_nota(String des_nota) {
            this.des_nota = des_nota;
        }

        public String getDes_config() {
            return des_config;
        }

        public void setDes_config(String des_config) {
            this.des_config = des_config;
        }
    }



}
