package com.coppel.rhconecta.dev.business.models;

public class LetterConfigResponse extends CoppelGeneralParameterResponse {
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
        private String Clave;
        private String Mensaje;
        private Datos[] datosCarta;
        private String errorCode;
        private String userMessage;

        public String getClave() {
            return Clave;
        }

        public void setClave(String clave) {
            Clave = clave;
        }

        public String getMensaje() {
            return Mensaje;
        }

        public void setMensaje(String mensaje) {
            Mensaje = mensaje;
        }

        public Datos[] getDatosCarta() {
            return datosCarta;
        }

        public void setDatosCarta(Datos[] datosCarta) {
            this.datosCarta = datosCarta;
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

    public class Datos {
        private String idu_datoscartas;
        private String idu_defaultdatoscarta;
        private String nom_datoscartas;
        private String opc_estatus;
    }
}
