package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class HolidaysReasonsAditionalDaysResponse extends HolidaysBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable{

        private List<Response> response;

        public List<Response> getResponse() {
            return response;
        }

        public void setResponse(List<Response> response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {

        private List<ReasonAditionaDay> motivos;
        private int idu_estado;


        public List<ReasonAditionaDay> getMotivos() {
            return motivos;
        }

        public void setMotivos(List<ReasonAditionaDay> motivos) {
            this.motivos = motivos;
        }

        public int getIdu_estado() {
            return idu_estado;
        }

        public void setIdu_estado(int idu_estado) {
            this.idu_estado = idu_estado;
        }
    }
}
