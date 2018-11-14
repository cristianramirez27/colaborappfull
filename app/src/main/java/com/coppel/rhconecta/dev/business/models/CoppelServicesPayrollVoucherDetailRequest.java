package com.coppel.rhconecta.dev.business.models;

public class CoppelServicesPayrollVoucherDetailRequest {
    private String num_empleado;
    private String correo;
    private int tipo_Constancia;
    private int solicitud;
    private int opcionEnvio;
    private String fecha;
    private PayrollVoucherDetailGenericData datos;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTipo_Constancia() {
        return tipo_Constancia;
    }

    public void setTipo_Constancia(int tipo_Constancia) {
        this.tipo_Constancia = tipo_Constancia;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public int getOpcionEnvio() {
        return opcionEnvio;
    }

    public void setOpcionEnvio(int opcionEnvio) {
        this.opcionEnvio = opcionEnvio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public PayrollVoucherDetailGenericData getDatos() {
        return datos;
    }

    public void setDatos(PayrollVoucherDetailGenericData datos) {
        this.datos = datos;
    }

    public static class PayrollVoucherDetailGenericData {

    }

    public static class Datos extends PayrollVoucherDetailGenericData{
        private String clv_rfc;
        private String nom_beneficiario;
        private int num_beneficiario;

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



}
