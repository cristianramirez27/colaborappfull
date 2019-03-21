package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class ConsultaMetodosPagoResponse extends WithDrawSavingBaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private List<PaymentWay> response;

        public List<PaymentWay> getResponse() {
            return response;
        }

        public void setResponse(List<PaymentWay> response) {
            this.response = response;
        }
    }



    public class PaymentWay{
        private int clv_retiro;
        private String nom_retiro;
        private int imp_disponible;

        private boolean isSelected;

        public int getClv_retiro() {
            return clv_retiro;
        }

        public void setClv_retiro(int clv_retiro) {
            this.clv_retiro = clv_retiro;
        }

        public String getNom_retiro() {
            return nom_retiro;
        }

        public void setNom_retiro(String nom_retiro) {
            this.nom_retiro = nom_retiro;
        }

        public int getImp_disponible() {
            return imp_disponible;
        }

        public void setImp_disponible(int imp_disponible) {
            this.imp_disponible = imp_disponible;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
