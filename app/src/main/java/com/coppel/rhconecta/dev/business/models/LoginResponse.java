package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

public class LoginResponse extends CoppelGeneralParameterResponse implements Serializable {

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

    public class Response implements Serializable {
        private String app;
        private String cliente;
        private String email;
        private String id_dispositivo;
        private String nombre;
        private String password;
        private String tarjeta;
        private String tipoCliente;
        private String token;
        private int errorCode;
        private String userMessage;

        private String token_user;

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId_dispositivo() {
            return id_dispositivo;
        }

        public void setId_dispositivo(String id_dispositivo) {
            this.id_dispositivo = id_dispositivo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTarjeta() {
            return tarjeta;
        }

        public void setTarjeta(String tarjeta) {
            this.tarjeta = tarjeta;
        }

        public String getTipoCliente() {
            return tipoCliente;
        }

        public void setTipoCliente(String tipoCliente) {
            this.tipoCliente = tipoCliente;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }


        public String getToken_user() {
            return token_user;
        }

        public void setToken_user(String token_user) {
            this.token_user = token_user;
        }
    }
}
