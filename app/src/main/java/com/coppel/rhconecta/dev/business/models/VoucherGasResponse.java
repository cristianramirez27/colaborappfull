package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class VoucherGasResponse extends CoppelGeneralParameterResponse {
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

        private List<List<DatosGas>> datosgasolina;
        private String errorCode;
        private String userMessage;

        public List<List<DatosGas>> getDatosgasolina() {
            return datosgasolina;
        }

        public void setDatosgasolina(List<List<DatosGas>> datosgasolina) {
            this.datosgasolina = datosgasolina;
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

    public class DatosGas {
        private int adicional;
        private int centro;
        private int chofer;
        private int empleado;
        private int factor;
        private String factor2;
        private String factura;
        private String fecha_captura;
        private String fecha_vale;
        private String fechacorte;
        private int importe;
        private String importe2;
        private String iniciales;
        private String nombre;
        private String nombrecentro;
        private String nombreproveedor;
        private String nomchofer;
        private int numerociudad;
        private int proveedor;
        private int retencion;
        private String retencion2;
        private int total;
        private String total2;
        private int unidad;
        private String valen;

        public int getAdicional() {
            return adicional;
        }

        public void setAdicional(int adicional) {
            this.adicional = adicional;
        }

        public int getCentro() {
            return centro;
        }

        public void setCentro(int centro) {
            this.centro = centro;
        }

        public int getChofer() {
            return chofer;
        }

        public void setChofer(int chofer) {
            this.chofer = chofer;
        }

        public int getEmpleado() {
            return empleado;
        }

        public void setEmpleado(int empleado) {
            this.empleado = empleado;
        }

        public int getFactor() {
            return factor;
        }

        public void setFactor(int factor) {
            this.factor = factor;
        }

        public String getFactor2() {
            return factor2;
        }

        public void setFactor2(String factor2) {
            this.factor2 = factor2;
        }

        public String getFactura() {
            return factura;
        }

        public void setFactura(String factura) {
            this.factura = factura;
        }

        public String getFecha_captura() {
            return fecha_captura;
        }

        public void setFecha_captura(String fecha_captura) {
            this.fecha_captura = fecha_captura;
        }

        public String getFecha_vale() {
            return fecha_vale;
        }

        public void setFecha_vale(String fecha_vale) {
            this.fecha_vale = fecha_vale;
        }

        public String getFechacorte() {
            return fechacorte;
        }

        public void setFechacorte(String fechacorte) {
            this.fechacorte = fechacorte;
        }

        public int getImporte() {
            return importe;
        }

        public void setImporte(int importe) {
            this.importe = importe;
        }

        public String getImporte2() {
            return importe2;
        }

        public void setImporte2(String importe2) {
            this.importe2 = importe2;
        }

        public String getIniciales() {
            return iniciales;
        }

        public void setIniciales(String iniciales) {
            this.iniciales = iniciales;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombrecentro() {
            return nombrecentro;
        }

        public void setNombrecentro(String nombrecentro) {
            this.nombrecentro = nombrecentro;
        }

        public String getNombreproveedor() {
            return nombreproveedor;
        }

        public void setNombreproveedor(String nombreproveedor) {
            this.nombreproveedor = nombreproveedor;
        }

        public String getNomchofer() {
            return nomchofer;
        }

        public void setNomchofer(String nomchofer) {
            this.nomchofer = nomchofer;
        }

        public int getNumerociudad() {
            return numerociudad;
        }

        public void setNumerociudad(int numerociudad) {
            this.numerociudad = numerociudad;
        }

        public int getProveedor() {
            return proveedor;
        }

        public void setProveedor(int proveedor) {
            this.proveedor = proveedor;
        }

        public int getRetencion() {
            return retencion;
        }

        public void setRetencion(int retencion) {
            this.retencion = retencion;
        }

        public String getRetencion2() {
            return retencion2;
        }

        public void setRetencion2(String retencion2) {
            this.retencion2 = retencion2;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getTotal2() {
            return total2;
        }

        public void setTotal2(String total2) {
            this.total2 = total2;
        }

        public int getUnidad() {
            return unidad;
        }

        public void setUnidad(int unidad) {
            this.unidad = unidad;
        }

        public String getValen() {
            return valen;
        }

        public void setValen(String valen) {
            this.valen = valen;
        }
    }
}
