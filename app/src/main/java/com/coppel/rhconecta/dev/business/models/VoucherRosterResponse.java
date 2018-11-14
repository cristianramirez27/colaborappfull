package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class VoucherRosterResponse extends CoppelGeneralParameterResponse {

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

    public class Response {

        private Collaborator colaborador;
        private List<IngresosEgreso> ingresosEgresos;
        private String errorCode;
        private String userMessage;

        public Collaborator getColaborador() {
            return colaborador;
        }

        public void setColaborador(Collaborator colaborador) {
            this.colaborador = colaborador;
        }

        public List<IngresosEgreso> getIngresosEgresos() {
            return ingresosEgresos;
        }

        public void setIngresosEgresos(List<IngresosEgreso> ingresosEgresos) {
            this.ingresosEgresos = ingresosEgresos;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }

    }

    public class IngresosEgreso {
        public String cdescripcionmovimiento;
        public String cpercepciondeduccion;
        public int itipomovimiento;
        public String mimporte;

        public String getCdescripcionmovimiento() {
            return cdescripcionmovimiento;
        }

        public void setCdescripcionmovimiento(String cdescripcionmovimiento) {
            this.cdescripcionmovimiento = cdescripcionmovimiento;
        }

        public String getCpercepciondeduccion() {
            return cpercepciondeduccion;
        }

        public void setCpercepciondeduccion(String cpercepciondeduccion) {
            this.cpercepciondeduccion = cpercepciondeduccion;
        }

        public int getItipomovimiento() {
            return itipomovimiento;
        }

        public void setItipomovimiento(int itipomovimiento) {
            this.itipomovimiento = itipomovimiento;
        }

        public String getMimporte() {
            return mimporte;
        }

        public void setMimporte(String mimporte) {
            this.mimporte = mimporte;
        }
    }

}
