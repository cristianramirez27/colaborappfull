package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class BenefitsCategoriesResponse extends BenefitsBaseResponse {
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

        private List<Category> Categorias;
        private DatosCiudad datosCiudad;
        private int Clave;
        private String Mensaje;

        public int getClave() {
            return Clave;
        }

        public void setClave(int clave) {
            Clave = clave;
        }

        public String getMensaje() {
            return Mensaje;
        }

        public void setMensaje(String mensaje) {
            Mensaje = mensaje;
        }

        public List<Category> getCategorias() {
            return Categorias;
        }

        public void setCategorias(List<Category> categorias) {
            Categorias = categorias;
        }


        public DatosCiudad getDatosCiudad() {
            return datosCiudad;
        }

        public void setDatosCiudad(DatosCiudad datosCiudad) {
            this.datosCiudad = datosCiudad;
        }
    }

    public class DatosCiudad implements Serializable{
        private int num_ciudad;
        private int num_estado;
        private String nombre_ciudad;

        public String getNombre_ciudad() {
            return nombre_ciudad;
        }

        public void setNombre_ciudad(String nombre_ciudad) {
            this.nombre_ciudad = nombre_ciudad;
        }

        public int getNum_ciudad() {
            return num_ciudad;
        }

        public void setNum_ciudad(int num_ciudad) {
            this.num_ciudad = num_ciudad;
        }

        public int getNum_estado() {
            return num_estado;
        }

        public void setNum_estado(int num_estado) {
            this.num_estado = num_estado;
        }
    }

    public class Category implements Serializable {
        private int cve;
        private String descripcion;
        private String logo;
        private String nombre;

        //Se agrega para los resultados de la busqueda

        private List<BenefitsDiscountsResponse.Discount> servicios;

        public int getCve() {
            return cve;
        }

        public void setCve(int cve) {
            this.cve = cve;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }


        public List<BenefitsDiscountsResponse.Discount> getServicios() {
            return servicios;
        }

        public void setServicios(List<BenefitsDiscountsResponse.Discount> servicios) {
            this.servicios = servicios;
        }
    }
}