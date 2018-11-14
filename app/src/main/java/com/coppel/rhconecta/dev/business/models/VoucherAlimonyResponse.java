package com.coppel.rhconecta.dev.business.models;

import java.util.List;

public class VoucherAlimonyResponse extends CoppelGeneralParameterResponse {
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

        private Collaborator colaborador;
        private List<IngresosEgreso> ingresosEgresos;
        private AlimonyData datosPension;
        private String errorCode;
        private String userMessage;

        public Collaborator getColaborador() {
            return colaborador;
        }

        public void setColaborador(Collaborator colaborador) {
            this.colaborador = colaborador;
        }

        public List<IngresosEgreso> getIngresosEgresos() {
            return ingresosEgresos;
        }

        public void setIngresosEgresos(List<IngresosEgreso> ingresosEgresos) {
            this.ingresosEgresos = ingresosEgresos;
        }

        public AlimonyData getDatosPension() {
            return datosPension;
        }

        public void setDatosPension(AlimonyData datosPension) {
            this.datosPension = datosPension;
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

    public class AlimonyData {

        private int aguinaldo;
        private int anticiporeparto;
        private String baseretencion;
        private String clv_curp;
        private String clv_rfcempleado;
        private String cuenta;
        private int despensa;
        private String fechaquincena;
        private int fondoahorro;
        private int fondoahorro_adicional;
        private int fondoahorro_ext_trabajador;
        private int horasextra;
        private int idu_centro;
        private int idu_empleado;
        private int idu_empresa;
        private int imss;
        private int incentivos;
        private int isr;
        private int isr_ajuste;
        private String nom_centro;
        private String nom_ciudad;
        private String nom_empresa;
        private String nom_estado;
        private String num_afiliacion;
        private String periodofinal;
        private String periodoinicial;
        private int porcentaje;
        private int primadominical;
        private int primavacacional;
        private String retencionpension;
        private int sueldo;
        private int tiponomina;
        private int tipotalon;
        private int utilidades;

        public int getAguinaldo() {
            return aguinaldo;
        }

        public void setAguinaldo(int aguinaldo) {
            this.aguinaldo = aguinaldo;
        }

        public int getAnticiporeparto() {
            return anticiporeparto;
        }

        public void setAnticiporeparto(int anticiporeparto) {
            this.anticiporeparto = anticiporeparto;
        }

        public String getBaseretencion() {
            return baseretencion;
        }

        public void setBaseretencion(String baseretencion) {
            this.baseretencion = baseretencion;
        }

        public String getClv_curp() {
            return clv_curp;
        }

        public void setClv_curp(String clv_curp) {
            this.clv_curp = clv_curp;
        }

        public String getClv_rfcempleado() {
            return clv_rfcempleado;
        }

        public void setClv_rfcempleado(String clv_rfcempleado) {
            this.clv_rfcempleado = clv_rfcempleado;
        }

        public String getCuenta() {
            return cuenta;
        }

        public void setCuenta(String cuenta) {
            this.cuenta = cuenta;
        }

        public int getDespensa() {
            return despensa;
        }

        public void setDespensa(int despensa) {
            this.despensa = despensa;
        }

        public String getFechaquincena() {
            return fechaquincena;
        }

        public void setFechaquincena(String fechaquincena) {
            this.fechaquincena = fechaquincena;
        }

        public int getFondoahorro() {
            return fondoahorro;
        }

        public void setFondoahorro(int fondoahorro) {
            this.fondoahorro = fondoahorro;
        }

        public int getFondoahorro_adicional() {
            return fondoahorro_adicional;
        }

        public void setFondoahorro_adicional(int fondoahorro_adicional) {
            this.fondoahorro_adicional = fondoahorro_adicional;
        }

        public int getFondoahorro_ext_trabajador() {
            return fondoahorro_ext_trabajador;
        }

        public void setFondoahorro_ext_trabajador(int fondoahorro_ext_trabajador) {
            this.fondoahorro_ext_trabajador = fondoahorro_ext_trabajador;
        }

        public int getHorasextra() {
            return horasextra;
        }

        public void setHorasextra(int horasextra) {
            this.horasextra = horasextra;
        }

        public int getIdu_centro() {
            return idu_centro;
        }

        public void setIdu_centro(int idu_centro) {
            this.idu_centro = idu_centro;
        }

        public int getIdu_empleado() {
            return idu_empleado;
        }

        public void setIdu_empleado(int idu_empleado) {
            this.idu_empleado = idu_empleado;
        }

        public int getIdu_empresa() {
            return idu_empresa;
        }

        public void setIdu_empresa(int idu_empresa) {
            this.idu_empresa = idu_empresa;
        }

        public int getImss() {
            return imss;
        }

        public void setImss(int imss) {
            this.imss = imss;
        }

        public int getIncentivos() {
            return incentivos;
        }

        public void setIncentivos(int incentivos) {
            this.incentivos = incentivos;
        }

        public int getIsr() {
            return isr;
        }

        public void setIsr(int isr) {
            this.isr = isr;
        }

        public int getIsr_ajuste() {
            return isr_ajuste;
        }

        public void setIsr_ajuste(int isr_ajuste) {
            this.isr_ajuste = isr_ajuste;
        }

        public String getNom_centro() {
            return nom_centro;
        }

        public void setNom_centro(String nom_centro) {
            this.nom_centro = nom_centro;
        }

        public String getNom_ciudad() {
            return nom_ciudad;
        }

        public void setNom_ciudad(String nom_ciudad) {
            this.nom_ciudad = nom_ciudad;
        }

        public String getNom_empresa() {
            return nom_empresa;
        }

        public void setNom_empresa(String nom_empresa) {
            this.nom_empresa = nom_empresa;
        }

        public String getNom_estado() {
            return nom_estado;
        }

        public void setNom_estado(String nom_estado) {
            this.nom_estado = nom_estado;
        }

        public String getNum_afiliacion() {
            return num_afiliacion;
        }

        public void setNum_afiliacion(String num_afiliacion) {
            this.num_afiliacion = num_afiliacion;
        }

        public String getPeriodofinal() {
            return periodofinal;
        }

        public void setPeriodofinal(String periodofinal) {
            this.periodofinal = periodofinal;
        }

        public String getPeriodoinicial() {
            return periodoinicial;
        }

        public void setPeriodoinicial(String periodoinicial) {
            this.periodoinicial = periodoinicial;
        }

        public int getPorcentaje() {
            return porcentaje;
        }

        public void setPorcentaje(int porcentaje) {
            this.porcentaje = porcentaje;
        }

        public int getPrimadominical() {
            return primadominical;
        }

        public void setPrimadominical(int primadominical) {
            this.primadominical = primadominical;
        }

        public int getPrimavacacional() {
            return primavacacional;
        }

        public void setPrimavacacional(int primavacacional) {
            this.primavacacional = primavacacional;
        }

        public String getRetencionpension() {
            return retencionpension;
        }

        public void setRetencionpension(String retencionpension) {
            this.retencionpension = retencionpension;
        }

        public int getSueldo() {
            return sueldo;
        }

        public void setSueldo(int sueldo) {
            this.sueldo = sueldo;
        }

        public int getTiponomina() {
            return tiponomina;
        }

        public void setTiponomina(int tiponomina) {
            this.tiponomina = tiponomina;
        }

        public int getTipotalon() {
            return tipotalon;
        }

        public void setTipotalon(int tipotalon) {
            this.tipotalon = tipotalon;
        }

        public int getUtilidades() {
            return utilidades;
        }

        public void setUtilidades(int utilidades) {
            this.utilidades = utilidades;
        }
    }

    public class IngresosEgreso {

        private String cdescripcionmovimiento;
        private String cpercepciondeduccion;
        private int itipomovimiento;
        private String mimporte;

        public String getCdescripcionmovimiento() {
            return cdescripcionmovimiento;
        }

        public void setCdescripcionmovimiento(String cdescripcionmovimiento) {
            this.cdescripcionmovimiento = cdescripcionmovimiento;
        }

        public String getCpercepciondeduccion() {
            return cpercepciondeduccion;
        }

        public void setCpercepciondeduccion(String cpercepciondeduccion) {
            this.cpercepciondeduccion = cpercepciondeduccion;
        }

        public int getItipomovimiento() {
            return itipomovimiento;
        }

        public void setItipomovimiento(int itipomovimiento) {
            this.itipomovimiento = itipomovimiento;
        }

        public String getMimporte() {
            return mimporte;
        }

        public void setMimporte(String mimporte) {
            this.mimporte = mimporte;
        }
    }
}
