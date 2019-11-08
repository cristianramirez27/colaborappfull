package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class VoucherResponseV2 extends VoucherResponseGeneric {
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

        public List<OpcionNomina> Opciones_nomina;


        public List<OpcionNomina> getOpciones_nomina() {
            return Opciones_nomina;
        }

        public void setOpciones_nomina(List<OpcionNomina> opciones_nomina) {
            Opciones_nomina = opciones_nomina;
        }
    }

    public class OpcionNomina {

        public int itipocomprobante;
        public String sfechanomina;
        public String sfechanominanombre;
        public String sfechanominanombre2;
        public String stotalapagar;


        public int getItipocomprobante() {
            return itipocomprobante;
        }

        public void setItipocomprobante(int itipocomprobante) {
            this.itipocomprobante = itipocomprobante;
        }

        public String getSfechanomina() {
            return sfechanomina;
        }

        public void setSfechanomina(String sfechanomina) {
            this.sfechanomina = sfechanomina;
        }

        public String getSfechanominanombre() {
            return sfechanominanombre;
        }

        public void setSfechanominanombre(String sfechanominanombre) {
            this.sfechanominanombre = sfechanominanombre;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }
    }
}
