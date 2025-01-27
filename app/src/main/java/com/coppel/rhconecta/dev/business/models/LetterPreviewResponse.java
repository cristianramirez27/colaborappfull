package com.coppel.rhconecta.dev.business.models;

public class LetterPreviewResponse extends CoppelGeneralParameterResponse {
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
        private String cuerpo;
        private String encabezado1;
        private String encabezado2;
        private String pie1;
        private String pie2;
        private String errorCode;
        private String userMessage;

        public String getCuerpo() {
            return cuerpo;
        }

        public void setCuerpo(String cuerpo) {
            this.cuerpo = cuerpo;
        }

        public String getEncabezado1() {
            return encabezado1;
        }

        public void setEncabezado1(String encabezado1) {
            this.encabezado1 = encabezado1;
        }

        public String getEncabezado2() {
            return encabezado2;
        }

        public void setEncabezado2(String encabezado2) {
            this.encabezado2 = encabezado2;
        }

        public String getPie1() {
            return pie1;
        }

        public void setPie1(String pie1) {
            this.pie1 = pie1;
        }

        public String getPie2() {
            return pie2;
        }

        public void setPie2(String pie2) {
            this.pie2 = pie2;
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
