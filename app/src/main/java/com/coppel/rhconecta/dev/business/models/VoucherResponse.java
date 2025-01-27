package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class VoucherResponse extends VoucherResponseGeneric {
    private Data data;

    int typeSelected;

    public int getTypeSelected() {
        return typeSelected;
    }

    public void setTypeSelected(int typeSelected) {
        this.typeSelected = typeSelected;
    }

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

        public List<DatosPencion> DatosPencion;
        public List<FechaCorteCuenta> Fecha_Corte_Cuenta;
        public List<FechaGasolina> Fecha_Gasolina;
        public List<FechasAguinaldo> Fechas_Aguinaldo;
        public List<FechasUtilidade> Fechas_Utilidades;
        public List<FechasNomina> Fechas_nominas;
        public List<FechasPensione> fechasPensiones;
        private String errorCode;
        private String userMessage;

        public List<VoucherResponse.DatosPencion> getDatosPencion() {
            return DatosPencion;
        }

        public void setDatosPencion(List<VoucherResponse.DatosPencion> datosPencion) {
            DatosPencion = datosPencion;
        }

        public List<FechaCorteCuenta> getFecha_Corte_Cuenta() {
            return Fecha_Corte_Cuenta;
        }

        public void setFecha_Corte_Cuenta(List<FechaCorteCuenta> fecha_Corte_Cuenta) {
            Fecha_Corte_Cuenta = fecha_Corte_Cuenta;
        }

        public List<FechaGasolina> getFecha_Gasolina() {
            return Fecha_Gasolina;
        }

        public void setFecha_Gasolina(List<FechaGasolina> fecha_Gasolina) {
            Fecha_Gasolina = fecha_Gasolina;
        }

        public List<FechasAguinaldo> getFechas_Aguinaldo() {
            return Fechas_Aguinaldo;
        }

        public void setFechas_Aguinaldo(List<FechasAguinaldo> fechas_Aguinaldo) {
            Fechas_Aguinaldo = fechas_Aguinaldo;
        }

        public List<FechasUtilidade> getFechas_Utilidades() {
            return Fechas_Utilidades;
        }

        public void setFechas_Utilidades(List<FechasUtilidade> fechas_Utilidades) {
            Fechas_Utilidades = fechas_Utilidades;
        }

        public List<FechasNomina> getFechas_nominas() {
            return Fechas_nominas;
        }

        public void setFechas_nominas(List<FechasNomina> fechas_nominas) {
            Fechas_nominas = fechas_nominas;
        }

        public List<FechasPensione> getFechasPensiones() {
            return fechasPensiones;
        }

        public void setFechasPensiones(List<FechasPensione> fechasPensiones) {
            this.fechasPensiones = fechasPensiones;
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

    public class DatosPencion {

        public String clv_rfc;
        public String nom_beneficiario;
        public int num_beneficiario;

        public String getClv_rfc() {
            return clv_rfc;
        }

        public void setClv_rfc(String clv_rfc) {
            this.clv_rfc = clv_rfc;
        }

        public String getNom_beneficiario() {
            return nom_beneficiario;
        }

        public void setNom_beneficiario(String nom_beneficiario) {
            this.nom_beneficiario = nom_beneficiario;
        }

        public int getNum_beneficiario() {
            return num_beneficiario;
        }

        public void setNum_beneficiario(int num_beneficiario) {
            this.num_beneficiario = num_beneficiario;
        }
    }

    public class FechaCorteCuenta extends AdapterData {

        public String sfechanomina;
        public String sfechanominanombre;
        public String stotalapagar;
        private String sfechanominanombre2;
        private VoucherSavingFundResponse voucherSavingFundResponse;

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

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public VoucherSavingFundResponse getVoucherSavingFundResponse() {
            return voucherSavingFundResponse;
        }

        public void setVoucherSavingFundResponse(VoucherSavingFundResponse voucherSavingFundResponse) {
            this.voucherSavingFundResponse = voucherSavingFundResponse;
        }
    }

    public class FechaGasolina extends AdapterData {

        public String sfechanomina;
        public String sfechanominanombre;
        public String stotalapagar;
        private String sfechanominanombre2;
        private VoucherGasResponse voucherGasResponse;

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

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public VoucherGasResponse getVoucherGasResponse() {
            return voucherGasResponse;
        }

        public void setVoucherGasResponse(VoucherGasResponse voucherGasResponse) {
            this.voucherGasResponse = voucherGasResponse;
        }
    }

    public class FechasAguinaldo extends AdapterData {

        public String sfechanomina;
        public String sfechanominanombre;
        public String stotalapagar;
        private String sfechanominanombre2;
        private VoucherBonusResponse voucherBonusResponse;

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

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public VoucherBonusResponse getVoucherBonusResponse() {
            return voucherBonusResponse;
        }

        public void setVoucherBonusResponse(VoucherBonusResponse voucherBonusResponse) {
            this.voucherBonusResponse = voucherBonusResponse;
        }
    }

    public class FechasUtilidade extends AdapterData {

        public String sfechanomina;
        public String sfechanominanombre;
        public String stotalapagar;
        private String sfechanominanombre2;
        private VoucherPTUResponse voucherPTUResponse;

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

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public VoucherPTUResponse getVoucherPTUResponse() {
            return voucherPTUResponse;
        }

        public void setVoucherPTUResponse(VoucherPTUResponse voucherPTUResponse) {
            this.voucherPTUResponse = voucherPTUResponse;
        }
    }

    public class FechasNomina extends AdapterData {

        public String sfechanomina;
        public String sfechanominanombre;
        public String stotalapagar;
        private String sfechanominanombre2;
        private VoucherRosterResponse voucherRosterResponse;

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

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public String getSfechanominanombre2() {
            return sfechanominanombre2;
        }

        public void setSfechanominanombre2(String sfechanominanombre2) {
            this.sfechanominanombre2 = sfechanominanombre2;
        }

        public VoucherRosterResponse getVoucherRosterResponse() {
            return voucherRosterResponse;
        }

        public void setVoucherRosterResponse(VoucherRosterResponse voucherRosterResponse) {
            this.voucherRosterResponse = voucherRosterResponse;
        }
    }

    public class FechasPensione extends AdapterData {

        public String sfechanomina;
        public String stotalapagar;
        private int num_beneficiario;
        private VoucherAlimonyResponse voucherAlimonyResponse;

        public String getSfechanomina() {
            return sfechanomina;
        }

        public void setSfechanomina(String sfechanomina) {
            this.sfechanomina = sfechanomina;
        }

        public String getStotalapagar() {
            return stotalapagar;
        }

        public void setStotalapagar(String stotalapagar) {
            this.stotalapagar = stotalapagar;
        }

        public int getNum_beneficiario() {
            return num_beneficiario;
        }

        public void setNum_beneficiario(int num_beneficiario) {
            this.num_beneficiario = num_beneficiario;
        }

        public VoucherAlimonyResponse getVoucherAlimonyResponse() {
            return voucherAlimonyResponse;
        }

        public void setVoucherAlimonyResponse(VoucherAlimonyResponse voucherAlimonyResponse) {
            this.voucherAlimonyResponse = voucherAlimonyResponse;
        }
    }

    public class AdapterData {

        private boolean isExpanded;
        private boolean failDetail;

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public boolean isFailDetail() {
            return failDetail;
        }

        public void setFailDetail(boolean failDetail) {
            this.failDetail = failDetail;
        }
    }
}
