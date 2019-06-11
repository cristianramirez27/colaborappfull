package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class DetailControlColaboratorResponse extends ExpensesTravelBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        private List<DetailRequest> GastoAutorizado;
        private List<DetailRequest> GastoComprobado;
        private List<DetailRequest> Faltante;
        private List<Devolution> Devoluciones;
        private List<Balance> SaldoTotal;
        private List<ReasonTravel> MotivoViaje;
        private List<Itinerary> Itinerario;
        private List<AditionalTraveller> ViajerosAdicionales;
        private List<Acomodation> TipoHospedaje;
        private List<Observation> Observaciones;

        private List<ItinerayMulti> ItinerarioMulticiudad;

        private int Estado;
        private String Mensaje;

        public int getEstado() {
            return Estado;
        }

        public void setEstado(int estado) {
            Estado = estado;
        }

        public String getMensaje() {
            return Mensaje;
        }

        public void setMensaje(String mensaje) {
            Mensaje = mensaje;
        }

        public List<DetailRequest> getGastoAutorizado() {
            return GastoAutorizado;
        }

        public void setGastoAutorizado(List<DetailRequest> gastoAutorizado) {
            GastoAutorizado = gastoAutorizado;
        }

        public List<DetailRequest> getGastoComprobado() {
            return GastoComprobado;
        }

        public void setGastoComprobado(List<DetailRequest> gastoComprobado) {
            GastoComprobado = gastoComprobado;
        }

        public List<DetailRequest> getFaltante() {
            return Faltante;
        }

        public void setFaltante(List<DetailRequest> faltante) {
            Faltante = faltante;
        }

        public List<Devolution> getDevoluciones() {
            return Devoluciones;
        }

        public void setDevoluciones(List<Devolution> devoluciones) {
            Devoluciones = devoluciones;
        }

        public List<Balance> getSaldoTotal() {
            return SaldoTotal;
        }

        public void setSaldoTotal(List<Balance> saldoTotal) {
            SaldoTotal = saldoTotal;
        }

        public List<ReasonTravel> getMotivoViaje() {
            return MotivoViaje;
        }

        public void setMotivoViaje(List<ReasonTravel> motivoViaje) {
            MotivoViaje = motivoViaje;
        }

        public List<Itinerary> getItinerario() {
            return Itinerario;
        }

        public void setItinerario(List<Itinerary> itinerario) {
            Itinerario = itinerario;
        }

        public List<AditionalTraveller> getViajerosAdicionales() {
            return ViajerosAdicionales;
        }

        public void setViajerosAdicionales(List<AditionalTraveller> viajerosAdicionales) {
            ViajerosAdicionales = viajerosAdicionales;
        }

        public List<Acomodation> getTipoHospedaje() {
            return TipoHospedaje;
        }

        public void setTipoHospedaje(List<Acomodation> tipoHospedaje) {
            TipoHospedaje = tipoHospedaje;
        }

        public List<Observation> getObservaciones() {
            return Observaciones;
        }

        public void setObservaciones(List<Observation> observaciones) {
            Observaciones = observaciones;
        }

        public List<ItinerayMulti> getItinerarioMulticiudad() {
            return ItinerarioMulticiudad;
        }

        public void setItinerarioMulticiudad(List<ItinerayMulti> itinerarioMulticiudad) {
            ItinerarioMulticiudad = itinerarioMulticiudad;
        }
    }

    public class Balance  implements Serializable{

        private double saldo_total;


        public double getSaldo_total() {
            return saldo_total;
        }

        public void setSaldo_total(double saldo_total) {
            this.saldo_total = saldo_total;
        }
    }



}
