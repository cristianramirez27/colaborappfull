package com.coppel.rhconecta.dev.business.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ColaboratorRequestsListExpensesResponse extends ExpensesTravelBaseResponse {
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
        private List<ControlColaborator> Controles;
        private List<Months> Meses;
        @SerializedName("Solicitudes-Complementos")
        private List<RequestComplementsColaborator> Solicitudes_Complementos;

        public List<ControlColaborator> getControles() {
            return Controles;
        }

        public void setControles(List<ControlColaborator> controles) {
            Controles = controles;
        }

        public List<Months> getMeses() {
            return Meses;
        }

        public void setMeses(List<Months> meses) {
            Meses = meses;
        }

        public List<RequestComplementsColaborator> getSolicitudes_Complementos() {
            return Solicitudes_Complementos;
        }

        public void setSolicitudes_Complementos(List<RequestComplementsColaborator> solicitudes_Complementos) {
            Solicitudes_Complementos = solicitudes_Complementos;
        }
    }

    public class ControlColaborator implements Serializable {
        private String control;
        private int estado;
        private int clv_estatus;
        private String estatus;
        private String fec_regreso;
        private String fec_salida;
        private String des_color;
        private String des_colorletra;
        private String itinerario;
        private String razon_viaje;

        private String nom_viajero;
        private String num_autorizo;

        private int num_viajero;


        public String getDes_colorletra() {
            return des_colorletra;
        }

        public void setDes_colorletra(String des_colorletra) {
            this.des_colorletra = des_colorletra;
        }

        public String getNom_viajero() {
            return nom_viajero;
        }

        public void setNom_viajero(String nom_viajero) {
            this.nom_viajero = nom_viajero;
        }

        public String getNomviajero() {
            return nom_viajero;
        }

        public int getNum_viajero() {
            return num_viajero;
        }

        public void setNum_viajero(int num_viajero) {
            this.num_viajero = num_viajero;
        }

        public void setNomviajero(String nomviajero) {
            this.nom_viajero = nomviajero;
        }

        public String getNum_autorizo() {
            return num_autorizo;
        }

        public void setNum_autorizo(String num_autorizo) {
            this.num_autorizo = num_autorizo;
        }

        public String getDes_color(){
            return des_color;
        }

        public void setDes_color(String des_color) {
            this.des_color = des_color;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public int getClv_estatus() {
            return clv_estatus;
        }

        public void setClv_estatus(int clv_estatus) {
            this.clv_estatus = clv_estatus;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getFec_regreso() {
            return fec_regreso;
        }

        public void setFec_regreso(String fec_regreso) {
            this.fec_regreso = fec_regreso;
        }

        public String getFec_salida() {
            return fec_salida;
        }

        public void setFec_salida(String fec_salida) {
            this.fec_salida = fec_salida;
        }

        public String getItinerario() {
            return itinerario;
        }

        public void setItinerario(String itinerario) {
            this.itinerario = itinerario;
        }

        public String getRazon_viaje() {
            return razon_viaje;
        }

        public void setRazon_viaje(String razon_viaje) {
            this.razon_viaje = razon_viaje;
        }
    }

    public class RequestComplementsColaborator implements Serializable {
        private String CLV_CONTROL;
        private String FCierre;
        private int autorizado;
        private int clv_estatus;
        private int clv_solicitud;
        private String des_motivo;
        private String estatus;
        private String des_color;
        private String des_colorletra;
        private String fecha;
        private String fecha_rechazo;
        private String fechaautoriza;
        private String fecharegreso;
        private String fechasalida;
        private int importe;
        private String nom_motivo;
        private String nombreautorizo;
        private String nomviajero;
        private String num_autorizo;
        private int num_destino;
        private int num_origen;
        private int num_rechazo;
        private int numeviajero;
        private String rutapago;
        private int tipo;
        private String des_solicitud;
        private String itinerario;
        private String empalme;
        private String nom_estatus;

        private int tipoSolicitud;


        public String getDes_colorletra() {
            return des_colorletra;
        }

        public void setDes_colorletra(String des_colorletra) {
            this.des_colorletra = des_colorletra;
        }

        public String getDes_color() {
            return des_color;
        }

        public void setDes_color(String des_color) {
            this.des_color = des_color;
        }

        public String getCLV_CONTROL() {
            return CLV_CONTROL;
        }

        public void setCLV_CONTROL(String CLV_CONTROL) {
            this.CLV_CONTROL = CLV_CONTROL;
        }

        public String getFCierre() {
            return FCierre;
        }

        public void setFCierre(String FCierre) {
            this.FCierre = FCierre;
        }

        public int getAutorizado() {
            return autorizado;
        }

        public void setAutorizado(int autorizado) {
            this.autorizado = autorizado;
        }

        public int getClv_estatus() {
            return clv_estatus;
        }

        public void setClv_estatus(int clv_estatus) {
            this.clv_estatus = clv_estatus;
        }

        public int getClv_solicitud() {
            return clv_solicitud;
        }

        public void setClv_solicitud(int clv_solicitud) {
            this.clv_solicitud = clv_solicitud;
        }

        public String getDes_motivo() {
            return des_motivo;
        }

        public void setDes_motivo(String des_motivo) {
            this.des_motivo = des_motivo;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getFecha_rechazo() {
            return fecha_rechazo;
        }

        public void setFecha_rechazo(String fecha_rechazo) {
            this.fecha_rechazo = fecha_rechazo;
        }

        public String getFechaautoriza() {
            return fechaautoriza;
        }

        public void setFechaautoriza(String fechaautoriza) {
            this.fechaautoriza = fechaautoriza;
        }

        public String getFecharegreso() {
            return fecharegreso;
        }

        public void setFecharegreso(String fecharegreso) {
            this.fecharegreso = fecharegreso;
        }

        public String getFechasalida() {
            return fechasalida;
        }

        public void setFechasalida(String fechasalida) {
            this.fechasalida = fechasalida;
        }

        public int getImporte() {
            return importe;
        }

        public void setImporte(int importe) {
            this.importe = importe;
        }

        public String getNom_motivo() {
            return nom_motivo;
        }

        public void setNom_motivo(String nom_motivo) {
            this.nom_motivo = nom_motivo;
        }

        public String getNombreautorizo() {
            return nombreautorizo;
        }

        public void setNombreautorizo(String nombreautorizo) {
            this.nombreautorizo = nombreautorizo;
        }

        public String getNomviajero() {
            return nomviajero;
        }

        public void setNomviajero(String nomviajero) {
            this.nomviajero = nomviajero;
        }

        public String getNum_autorizo() {
            return num_autorizo;
        }

        public void setNum_autorizo(String num_autorizo) {
            this.num_autorizo = num_autorizo;
        }

        public int getNum_destino() {
            return num_destino;
        }

        public void setNum_destino(int num_destino) {
            this.num_destino = num_destino;
        }

        public int getNum_origen() {
            return num_origen;
        }

        public void setNum_origen(int num_origen) {
            this.num_origen = num_origen;
        }

        public int getNum_rechazo() {
            return num_rechazo;
        }

        public void setNum_rechazo(int num_rechazo) {
            this.num_rechazo = num_rechazo;
        }

        public int getNumeviajero() {
            return numeviajero;
        }

        public void setNumeviajero(int numeviajero) {
            this.numeviajero = numeviajero;
        }

        public String getRutapago() {
            return rutapago;
        }

        public void setRutapago(String rutapago) {
            this.rutapago = rutapago;
        }

        public int getTipo() {
            return tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public String getDes_solicitud() {
            return des_solicitud;
        }

        public void setDes_solicitud(String des_solicitud) {
            this.des_solicitud = des_solicitud;
        }

        public String getItinerario() {
            return itinerario;
        }

        public void setItinerario(String itinerario) {
            this.itinerario = itinerario;
        }

        public String getEmpalme() {
            return empalme;
        }

        public void setEmpalme(String empalme) {
            this.empalme = empalme;
        }

        public String getNom_estatus() {
            return nom_estatus;
        }

        public void setNom_estatus(String nom_estatus) {
            this.nom_estatus = nom_estatus;
        }

        public int getTipoSolicitud() {
            return tipoSolicitud;
        }

        public void setTipoSolicitud(int tipoSolicitud) {
            this.tipoSolicitud = tipoSolicitud;
        }
    }

    public class Months{
        private int clv_mes;
        private String des_mes;

        private String num_empleados;

        private transient boolean expand;

        public String getNum_empleados() {
            return num_empleados;
        }

        public void setNum_empleados(String num_empleados) {
            this.num_empleados = num_empleados;
        }

        private List<ColaboratorControlsMonthResponse.ControlMonth> monthRequest;

        public int getClv_mes() {
            return clv_mes;
        }

        public void setClv_mes(int clv_mes) {
            this.clv_mes = clv_mes;
        }

        public String getDes_mes() {
            return des_mes;
        }

        public void setDes_mes(String des_mes) {
            this.des_mes = des_mes;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public List<ColaboratorControlsMonthResponse.ControlMonth> getMonthRequest() {
            return monthRequest;
        }

        public void setMonthRequest(List<ColaboratorControlsMonthResponse.ControlMonth> monthRequest) {
            this.monthRequest = monthRequest;
        }
    }
}
