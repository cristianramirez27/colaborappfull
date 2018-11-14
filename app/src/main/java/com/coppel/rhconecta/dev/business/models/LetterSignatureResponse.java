package com.coppel.rhconecta.dev.business.models;

import java.util.ArrayList;

public class LetterSignatureResponse extends CoppelGeneralParameterResponse {

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
        private String idu_clave;
        private String des_mensaje;
        private ArrayList<RemainingLetters> CartasRestantes;
        private String errorCode;
        private String userMessage;

        public String getIdu_clave() {
            return idu_clave;
        }

        public void setIdu_clave(String idu_clave) {
            this.idu_clave = idu_clave;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }

        public ArrayList<RemainingLetters> getCartasRestantes() {
            return CartasRestantes;
        }

        public void setCartasRestantes(ArrayList<RemainingLetters> cartasRestantes) {
            CartasRestantes = cartasRestantes;
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

    public class RemainingLetters {
        private String TipoCarta;
        private String Impresiones;
        private String Limite;
        private String Restantes;
    }
}
