package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class BenefitsCitiesResponse extends BenefitsBaseResponse {
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

        private List<City> Ciudades;

        public List<City> getCiudades() {
            return Ciudades;
        }

        public void setCiudades(List<City> ciudades) {
            Ciudades = ciudades;
        }
    }

    public class City  extends LocationEntity {
        private int id_es;
        public int getId_es() {
            return id_es;
        }

        public void setId_es(int id_es) {
            this.id_es = id_es;
        }


    }
}
