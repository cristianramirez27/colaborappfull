package com.coppel.rhconecta.dev.business.models;

public class GuardarRetiroResponse extends WithDrawSavingBaseResponse {
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
        private int clv_clave;
        private String clv_folio;
        private String des_mensaje;
        private String fec_captura;
        private String hrs_captura;


        public int getClv_clave() {
            return clv_clave;
        }

        public void setClv_clave(int clv_clave) {
            this.clv_clave = clv_clave;
        }

        public String getClv_folio() {
            return clv_folio;
        }

        public void setClv_folio(String clv_folio) {
            this.clv_folio = clv_folio;
        }

        public String getDes_mensaje() {
            return des_mensaje;
        }

        public void setDes_mensaje(String des_mensaje) {
            this.des_mensaje = des_mensaje;
        }

        public String getFec_captura() {
            return fec_captura;
        }

        public void setFec_captura(String fec_captura) {
            this.fec_captura = fec_captura;
        }

        public String getHrs_captura() {
            return hrs_captura;
        }

        public void setHrs_captura(String hrs_captura) {
            this.hrs_captura = hrs_captura;
        }
    }
}
