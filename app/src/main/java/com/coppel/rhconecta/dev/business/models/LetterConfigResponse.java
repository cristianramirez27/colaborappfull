package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;
import java.util.List;

public class LetterConfigResponse extends CoppelGeneralParameterResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable{

        private Response response;
        private List<DatosHorarios> datosHorarios;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public List<DatosHorarios> getDatosHorarios() {
            return datosHorarios;
        }
    }

    public class Response  implements Serializable {
        private String Clave;
        private String Mensaje;
        private Datos[] datosCarta;
        private DatosGuarderia DatosGuarderia;
        private String errorCode;
        private String userMessage;

        public String getClave() {
            return Clave;
        }

        public void setClave(String clave) {
            Clave = clave;
        }

        public String getMensaje() {
            return Mensaje;
        }

        public void setMensaje(String mensaje) {
            Mensaje = mensaje;
        }

        public Datos[]  getDatosCarta() {
            return datosCarta;
        }

        public void setDatosCarta(Datos[] datosCarta) {
            this.datosCarta = datosCarta;
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

        public LetterConfigResponse.DatosGuarderia getDatosGuarderia() {
            return DatosGuarderia;
        }

        public void setDatosGuarderia(LetterConfigResponse.DatosGuarderia datosGuarderia) {
            DatosGuarderia = datosGuarderia;
        }
    }

    public class Datos  implements Serializable {
        private int idu_datoscartas;
        private int idu_defaultdatoscarta;
        private String nom_datoscartas;
        private int opc_estatus;

        private boolean isSelected;

        public int getIdu_datoscartas() {
            return idu_datoscartas;
        }

        public void setIdu_datoscartas(int idu_datoscartas) {
            this.idu_datoscartas = idu_datoscartas;
        }

        public int getIdu_defaultdatoscarta() {
            return idu_defaultdatoscarta;
        }

        public void setIdu_defaultdatoscarta(int idu_defaultdatoscarta) {
            this.idu_defaultdatoscarta = idu_defaultdatoscarta;
        }

        public String getNom_datoscartas() {
            return nom_datoscartas;
        }

        public void setNom_datoscartas(String nom_datoscartas) {
            this.nom_datoscartas = nom_datoscartas;
        }

        public int getOpc_estatus() {
            return opc_estatus;
        }

        public void setOpc_estatus(int opc_estatus) {
            this.opc_estatus = opc_estatus;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public class DatosHorarios implements Serializable {

        private String value;
        private boolean isSelected;


        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }


    public class DatosGuarderia  implements Serializable{
        private List<LetterChildrenData> Nombre_hijos;
        private DatosHorario datoshorario;

        public List<LetterChildrenData> getNombre_hijos() {
            return Nombre_hijos;
        }

        public void setNombre_hijos(List<LetterChildrenData> nombre_hijos) {
            Nombre_hijos = nombre_hijos;
        }

        public DatosHorario getDatoshorario() {
            return datoshorario;
        }

        public void setDatoshorario(DatosHorario datoshorario) {
            this.datoshorario = datoshorario;
        }
    }


    public class DatosHorario  implements Serializable{
        private List<HorarioComida> horario_Comida;
        private List<HorarioLaboral> horario_Laboral;
        private List<HorarioSeccion> horario_Seccion;

        public List<HorarioComida> getHorario_Comida() {
            return horario_Comida;
        }

        public void setHorario_Comida(List<HorarioComida> horario_Comida) {
            this.horario_Comida = horario_Comida;
        }

        public List<HorarioLaboral> getHorario_Laboral() {

           /* for (HorarioLaboral horarioLaboral : horario_Laboral)
                horarioLaboral.setSelected(false);*/

            return horario_Laboral;
        }

        public void setHorario_Laboral(List<HorarioLaboral> horario_Laboral) {
            this.horario_Laboral = horario_Laboral;
        }

        public List<HorarioSeccion> getHorario_Seccion() {
            return horario_Seccion;
        }

        public void setHorario_Seccion(List<HorarioSeccion> horario_Seccion) {
            this.horario_Seccion = horario_Seccion;
        }
    }

    public static class Horario implements Serializable{

        protected boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }

    public class HorarioComida extends Horario {
        private String hrs_comida;

        public String getHrs_comida() {
            return hrs_comida;
        }

        public void setHrs_comida(String hrs_comida) {
            this.hrs_comida = hrs_comida;
        }
    }

    public class HorarioLaboral extends Horario {
        private String hrs_laboral;

        public String getHrs_laboral() {
            return hrs_laboral;
        }

        public void setHrs_laboral(String hrs_laboral) {
            this.hrs_laboral = hrs_laboral;
        }
    }

    public static class DiaLaboral extends Horario {
        private String dayName;

        public DiaLaboral() {
        }

        public DiaLaboral(String dayName) {
            this.dayName = dayName;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }
    }


    public class HorarioSeccion extends Horario{
        private String DiaFin;
        private String DiaInicio;
        private int Numeroseccion;

        public String getDiaFin() {
            return DiaFin;
        }

        public void setDiaFin(String diaFin) {
            DiaFin = diaFin;
        }

        public String getDiaInicio() {
            return DiaInicio;
        }

        public void setDiaInicio(String diaInicio) {
            DiaInicio = diaInicio;
        }

        public int getNumeroseccion() {
            return Numeroseccion;
        }

        public void setNumeroseccion(int numeroseccion) {
            Numeroseccion = numeroseccion;
        }
    }


}
