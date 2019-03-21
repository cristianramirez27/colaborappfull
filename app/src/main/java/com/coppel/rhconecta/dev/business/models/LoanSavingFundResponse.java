package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class LoanSavingFundResponse extends CoppelGeneralParameterResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable{
        private String ahorroAdicional;
        private String cuentaCorriente;
        private String fondoEmpresa;
        private String fondoTrabajador;
        private String margenCompra;
        private String margenCredito;
        private String errorCode;
        private String userMessage;

        public String getAhorroAdicional() {
            return ahorroAdicional;
        }

        public void setAhorroAdicional(String ahorroAdicional) {
            this.ahorroAdicional = ahorroAdicional;
        }

        public String getCuentaCorriente() {
            return cuentaCorriente;
        }

        public void setCuentaCorriente(String cuentaCorriente) {
            this.cuentaCorriente = cuentaCorriente;
        }

        public String getFondoEmpresa() {
            return fondoEmpresa;
        }

        public void setFondoEmpresa(String fondoEmpresa) {
            this.fondoEmpresa = fondoEmpresa;
        }

        public String getFondoTrabajador() {
            return fondoTrabajador;
        }

        public void setFondoTrabajador(String fondoTrabajador) {
            this.fondoTrabajador = fondoTrabajador;
        }

        public String getMargenCompra() {
            return margenCompra;
        }

        public void setMargenCompra(String margenCompra) {
            this.margenCompra = margenCompra;
        }

        public String getMargenCredito() {
            return margenCredito;
        }

        public void setMargenCredito(String margenCredito) {
            this.margenCredito = margenCredito;
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
}
