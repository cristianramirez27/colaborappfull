package com.coppel.rhconecta.dev.business.models;

public class LoginResponse extends CoppelGeneralParameterResponse {

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
        private String app;
        private String cliente;
        private String email;
        private String id_dispositivo;
        private String nombre;
        private String password;
        private String tarjeta;
        private String tipoCliente;
        private String token;
        private String errorCode;
        private String userMessage;

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
