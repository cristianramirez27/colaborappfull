package com.coppel.rhconecta.dev.business.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoucherSavingFundResponse extends CoppelGeneralParameterResponse {
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
        private Movimientos movimientos;
        private String errorCode;
        private String userMessage;

        public Collaborator getColaborador() {
            return colaborador;
        }

        public void setColaborador(Collaborator colaborador) {
            this.colaborador = colaborador;
        }

        public Movimientos getMovimientos() {
            return movimientos;
        }

        public void setMovimientos(Movimientos movimientos) {
            this.movimientos = movimientos;
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

    public class Movimientos {

        @SerializedName("ahorroadicional ")
        public List<Ahorroadicional> ahorroadicional;
        @SerializedName("ahorroadicional2 ")
        public List<Ahorroadicional2> ahorroadicional2;
        @SerializedName("autoCop ")
        public List<AutoCop> autoCop;
        @SerializedName("cuentaCorriente ")
        public List<CuentaCorriente> cuentaCorriente;
        @SerializedName("faerColaborador ")
        public List<FaerColaborador> faerColaborador;
        @SerializedName("faerEmpresa")
        public List<FaerEmpresa> faerEmpresa;
        @SerializedName("fondoEmpresa ")
        public List<FondoEmpresa> fondoEmpresa;
        @SerializedName("fondotrabajadores ")
        public List<Fondotrabajadore> fondotrabajadores;
        @SerializedName("margenes")
        public List<Margene> margenes;

        public List<Ahorroadicional> getAhorroadicional() {
            return ahorroadicional;
        }

        public void setAhorroadicional(List<Ahorroadicional> ahorroadicional) {
            this.ahorroadicional = ahorroadicional;
        }

        public List<Ahorroadicional2> getAhorroadicional2() {
            return ahorroadicional2;
        }

        public void setAhorroadicional2(List<Ahorroadicional2> ahorroadicional2) {
            this.ahorroadicional2 = ahorroadicional2;
        }

        public List<AutoCop> getAutoCop() {
            return autoCop;
        }

        public void setAutoCop(List<AutoCop> autoCop) {
            this.autoCop = autoCop;
        }

        public List<CuentaCorriente> getCuentaCorriente() {
            return cuentaCorriente;
        }

        public void setCuentaCorriente(List<CuentaCorriente> cuentaCorriente) {
            this.cuentaCorriente = cuentaCorriente;
        }

        public List<FaerColaborador> getFaerColaborador() {
            return faerColaborador;
        }

        public void setFaerColaborador(List<FaerColaborador> faerColaborador) {
            this.faerColaborador = faerColaborador;
        }

        public List<FaerEmpresa> getFaerEmpresa() {
            return faerEmpresa;
        }

        public void setFaerEmpresa(List<FaerEmpresa> faerEmpresa) {
            this.faerEmpresa = faerEmpresa;
        }

        public List<FondoEmpresa> getFondoEmpresa() {
            return fondoEmpresa;
        }

        public void setFondoEmpresa(List<FondoEmpresa> fondoEmpresa) {
            this.fondoEmpresa = fondoEmpresa;
        }

        public List<Fondotrabajadore> getFondotrabajadores() {
            return fondotrabajadores;
        }

        public void setFondotrabajadores(List<Fondotrabajadore> fondotrabajadores) {
            this.fondotrabajadores = fondotrabajadores;
        }

        public List<Margene> getMargenes() {
            return margenes;
        }

        public void setMargenes(List<Margene> margenes) {
            this.margenes = margenes;
        }
    }

    public class Ahorroadicional {
        public String ahorroadicional;
        public String ahorroadicionaldes;

        public String getAhorroadicional() {
            return ahorroadicional;
        }

        public void setAhorroadicional(String ahorroadicional) {
            this.ahorroadicional = ahorroadicional;
        }

        public String getAhorroadicionaldes() {
            return ahorroadicionaldes;
        }

        public void setAhorroadicionaldes(String ahorroadicionaldes) {
            this.ahorroadicionaldes = ahorroadicionaldes;
        }
    }

    public class Ahorroadicional2 {
        public String ahorroadicional2;
        public String ahorroadicional2des;

        public String getAhorroadicional2() {
            return ahorroadicional2;
        }

        public void setAhorroadicional2(String ahorroadicional2) {
            this.ahorroadicional2 = ahorroadicional2;
        }

        public String getAhorroadicional2des() {
            return ahorroadicional2des;
        }

        public void setAhorroadicional2des(String ahorroadicional2des) {
            this.ahorroadicional2des = ahorroadicional2des;
        }
    }

    public class AutoCop {
        public String auntocopdes;
        public String auntocopimp;

        public String getAuntocopdes() {
            return auntocopdes;
        }

        public void setAuntocopdes(String auntocopdes) {
            this.auntocopdes = auntocopdes;
        }

        public String getAuntocopimp() {
            return auntocopimp;
        }

        public void setAuntocopimp(String auntocopimp) {
            this.auntocopimp = auntocopimp;
        }
    }

    public class CuentaCorriente {
        public String CuentaCorrientedes;
        public String CuentaCorrienteimp;

        public String getCuentaCorrientedes() {
            return CuentaCorrientedes;
        }

        public void setCuentaCorrientedes(String cuentaCorrientedes) {
            CuentaCorrientedes = cuentaCorrientedes;
        }

        public String getCuentaCorrienteimp() {
            return CuentaCorrienteimp;
        }

        public void setCuentaCorrienteimp(String cuentaCorrienteimp) {
            CuentaCorrienteimp = cuentaCorrienteimp;
        }
    }

    public class FaerColaborador {
        public String faerColaboradorDes;
        public String faerdesimpempleado;

        public String getFaerColaboradorDes() {
            return faerColaboradorDes;
        }

        public void setFaerColaboradorDes(String faerColaboradorDes) {
            this.faerColaboradorDes = faerColaboradorDes;
        }

        public String getFaerdesimpempleado() {
            return faerdesimpempleado;
        }

        public void setFaerdesimpempleado(String faerdesimpempleado) {
            this.faerdesimpempleado = faerdesimpempleado;
        }
    }

    public class FaerEmpresa {
        public String faerEmpresaDes;
        public String faerdesimpempresa;

        public String getFaerEmpresaDes() {
            return faerEmpresaDes;
        }

        public void setFaerEmpresaDes(String faerEmpresaDes) {
            this.faerEmpresaDes = faerEmpresaDes;
        }

        public String getFaerdesimpempresa() {
            return faerdesimpempresa;
        }

        public void setFaerdesimpempresa(String faerdesimpempresa) {
            this.faerdesimpempresa = faerdesimpempresa;
        }
    }

    public class FondoEmpresa {
        public String fondoEmpdes;
        public String fondoEmpimp;

        public String getFondoEmpdes() {
            return fondoEmpdes;
        }

        public void setFondoEmpdes(String fondoEmpdes) {
            this.fondoEmpdes = fondoEmpdes;
        }

        public String getFondoEmpimp() {
            return fondoEmpimp;
        }

        public void setFondoEmpimp(String fondoEmpimp) {
            this.fondoEmpimp = fondoEmpimp;
        }
    }

    public class Fondotrabajadore {
        public String fondotrabajado;
        public String fondotrabajadordes;

        public String getFondotrabajado() {
            return fondotrabajado;
        }

        public void setFondotrabajado(String fondotrabajado) {
            this.fondotrabajado = fondotrabajado;
        }

        public String getFondotrabajadordes() {
            return fondotrabajadordes;
        }

        public void setFondotrabajadordes(String fondotrabajadordes) {
            this.fondotrabajadordes = fondotrabajadordes;
        }
    }

    public class Margene {
        public String margen_Credito_des;
        public String margen_Credito_imp;
        public String margen_Compra_des;
        public String margen_Compra_imp;

        public String getMargen_Credito_des() {
            return margen_Credito_des;
        }

        public void setMargen_Credito_des(String margen_Credito_des) {
            this.margen_Credito_des = margen_Credito_des;
        }

        public String getMargen_Credito_imp() {
            return margen_Credito_imp;
        }

        public void setMargen_Credito_imp(String margen_Credito_imp) {
            this.margen_Credito_imp = margen_Credito_imp;
        }

        public String getMargen_Compra_des() {
            return margen_Compra_des;
        }

        public void setMargen_Compra_des(String margen_Compra_des) {
            this.margen_Compra_des = margen_Compra_des;
        }

        public String getMargen_Compra_imp() {
            return margen_Compra_imp;
        }

        public void setMargen_Compra_imp(String margen_Compra_imp) {
            this.margen_Compra_imp = margen_Compra_imp;
        }
    }
}
