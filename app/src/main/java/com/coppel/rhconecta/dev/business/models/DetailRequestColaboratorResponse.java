package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class DetailRequestColaboratorResponse extends ExpensesTravelBaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {

        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public class Response implements Serializable {
        private List<DetailRequest> VerDetallesSolicitud;
        private List<DetailRequest> VerDetallesComplemento;
        private List<ReasonTravel> MotivoViaje;
        private List<Itinerary> Itinerario;
        private List<ItinerayMulti> ItinerarioMulticiudad;
        private List<AditionalTraveller> ViajerosAdicionales;
        private List<Acomodation> TipoHospedaje;
        private List<Observation> Observaciones;
        private List<FotoPerfil> FotoPerfil;

        private int Estado;
        private String Mensaje;

        private boolean isGteRol;

        public int getEstado() {
            return Estado;
        }

        public List<com.coppel.rhconecta.dev.business.models.FotoPerfil> getFotoPerfil() {
            return FotoPerfil;
        }

        public void setFotoPerfil(List<com.coppel.rhconecta.dev.business.models.FotoPerfil> fotoPerfil) {
            FotoPerfil = fotoPerfil;
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

        public List<DetailRequest> getVerDetallesSolicitud() {
            return VerDetallesSolicitud;
        }

        public void setVerDetallesSolicitud(List<DetailRequest> verDetallesSolicitud) {
            VerDetallesSolicitud = verDetallesSolicitud;
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

        public List<DetailRequest> getVerDetallesComplemento() {
            return VerDetallesComplemento;
        }

        public void setVerDetallesComplemento(List<DetailRequest> verDetallesComplemento) {
            VerDetallesComplemento = verDetallesComplemento;
        }

        public List<ItinerayMulti> getItinerarioMulticiudad() {
            return ItinerarioMulticiudad;
        }

        public void setItinerarioMulticiudad(List<ItinerayMulti> itinerarioMulticiudad) {
            ItinerarioMulticiudad = itinerarioMulticiudad;
        }

        public boolean isGteRol() {
            return isGteRol;
        }

        public void setGteRol(boolean gteRol) {
            isGteRol = gteRol;
        }
    }




}
