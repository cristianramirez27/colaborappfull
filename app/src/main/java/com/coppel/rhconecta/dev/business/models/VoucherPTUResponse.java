package com.coppel.rhconecta.dev.business.models;

public class VoucherPTUResponse extends CoppelGeneralParameterResponse {
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

        private DatosUtilidades datosutilidades;
        private DatosUtilidades2[] datosutilidades2;
        private String errorCode;
        private String userMessage;

        public DatosUtilidades getDatosutilidades() {
            return datosutilidades;
        }

        public void setDatosutilidades(DatosUtilidades datosutilidades) {
            this.datosutilidades = datosutilidades;
        }

        public DatosUtilidades2[] getDatosutilidades2() {
            return datosutilidades2;
        }

        public void setDatosutilidades2(DatosUtilidades2[] datosutilidades2) {
            this.datosutilidades2 = datosutilidades2;
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

    public class DatosUtilidades {
        private String apellidomaterno;
        private String apellidopaterno;
        private int centro;
        private String curp;
        private String descripcioncentro;
        private String descripcionciudad;
        private String descripcionempresa;
        private String descripcionpuesto;
        private String descripcionrutapago;
        private int diastrabajados;
        private int empresa;
        private String fechaalta;
        private String fechabaja;
        private String fechafinal;
        private String fechainicio;
        private String fechanomina;
        private int importedespensa;
        private int importeseguro;
        private String nombre;
        private int numemp;
        private String numeroafiliacion;
        private int numerociudad;
        private int numeropuesto;
        private int numeroseguros;
        private String rfc;
        private int rutapago;
        private int sueldomensual;
        private String tarjetabanco;
        private String tarjetadespensa;
        private String totalapagar;
        private String totalegresos;
        private String totalingresos;
        private String totalapagar2;
        private String totalegresos2;
        private String totalingresos2;

        public String getApellidomaterno() {
            return apellidomaterno;
        }

        public void setApellidomaterno(String apellidomaterno) {
            this.apellidomaterno = apellidomaterno;
        }

        public String getApellidopaterno() {
            return apellidopaterno;
        }

        public void setApellidopaterno(String apellidopaterno) {
            this.apellidopaterno = apellidopaterno;
        }

        public int getCentro() {
            return centro;
        }

        public void setCentro(int centro) {
            this.centro = centro;
        }

        public String getCurp() {
            return curp;
        }

        public void setCurp(String curp) {
            this.curp = curp;
        }

        public String getDescripcioncentro() {
            return descripcioncentro;
        }

        public void setDescripcioncentro(String descripcioncentro) {
            this.descripcioncentro = descripcioncentro;
        }

        public String getDescripcionciudad() {
            return descripcionciudad;
        }

        public void setDescripcionciudad(String descripcionciudad) {
            this.descripcionciudad = descripcionciudad;
        }

        public String getDescripcionempresa() {
            return descripcionempresa;
        }

        public void setDescripcionempresa(String descripcionempresa) {
            this.descripcionempresa = descripcionempresa;
        }

        public String getDescripcionpuesto() {
            return descripcionpuesto;
        }

        public void setDescripcionpuesto(String descripcionpuesto) {
            this.descripcionpuesto = descripcionpuesto;
        }

        public String getDescripcionrutapago() {
            return descripcionrutapago;
        }

        public void setDescripcionrutapago(String descripcionrutapago) {
            this.descripcionrutapago = descripcionrutapago;
        }

        public int getDiastrabajados() {
            return diastrabajados;
        }

        public void setDiastrabajados(int diastrabajados) {
            this.diastrabajados = diastrabajados;
        }

        public int getEmpresa() {
            return empresa;
        }

        public void setEmpresa(int empresa) {
            this.empresa = empresa;
        }

        public String getFechaalta() {
            return fechaalta;
        }

        public void setFechaalta(String fechaalta) {
            this.fechaalta = fechaalta;
        }

        public String getFechabaja() {
            return fechabaja;
        }

        public void setFechabaja(String fechabaja) {
            this.fechabaja = fechabaja;
        }

        public String getFechafinal() {
            return fechafinal;
        }

        public void setFechafinal(String fechafinal) {
            this.fechafinal = fechafinal;
        }

        public String getFechainicio() {
            return fechainicio;
        }

        public void setFechainicio(String fechainicio) {
            this.fechainicio = fechainicio;
        }

        public String getFechanomina() {
            return fechanomina;
        }

        public void setFechanomina(String fechanomina) {
            this.fechanomina = fechanomina;
        }

        public int getImportedespensa() {
            return importedespensa;
        }

        public void setImportedespensa(int importedespensa) {
            this.importedespensa = importedespensa;
        }

        public int getImporteseguro() {
            return importeseguro;
        }

        public void setImporteseguro(int importeseguro) {
            this.importeseguro = importeseguro;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getNumemp() {
            return numemp;
        }

        public void setNumemp(int numemp) {
            this.numemp = numemp;
        }

        public String getNumeroafiliacion() {
            return numeroafiliacion;
        }

        public void setNumeroafiliacion(String numeroafiliacion) {
            this.numeroafiliacion = numeroafiliacion;
        }

        public int getNumerociudad() {
            return numerociudad;
        }

        public void setNumerociudad(int numerociudad) {
            this.numerociudad = numerociudad;
        }

        public int getNumeropuesto() {
            return numeropuesto;
        }

        public void setNumeropuesto(int numeropuesto) {
            this.numeropuesto = numeropuesto;
        }

        public int getNumeroseguros() {
            return numeroseguros;
        }

        public void setNumeroseguros(int numeroseguros) {
            this.numeroseguros = numeroseguros;
        }

        public String getRfc() {
            return rfc;
        }

        public void setRfc(String rfc) {
            this.rfc = rfc;
        }

        public int getRutapago() {
            return rutapago;
        }

        public void setRutapago(int rutapago) {
            this.rutapago = rutapago;
        }

        public int getSueldomensual() {
            return sueldomensual;
        }

        public void setSueldomensual(int sueldomensual) {
            this.sueldomensual = sueldomensual;
        }

        public String getTarjetabanco() {
            return tarjetabanco;
        }

        public void setTarjetabanco(String tarjetabanco) {
            this.tarjetabanco = tarjetabanco;
        }

        public String getTarjetadespensa() {
            return tarjetadespensa;
        }

        public void setTarjetadespensa(String tarjetadespensa) {
            this.tarjetadespensa = tarjetadespensa;
        }

        public String getTotalapagar() {
            return totalapagar;
        }

        public void setTotalapagar(String totalapagar) {
            this.totalapagar = totalapagar;
        }

        public String getTotalegresos() {
            return totalegresos;
        }

        public void setTotalegresos(String totalegresos) {
            this.totalegresos = totalegresos;
        }

        public String getTotalingresos() {
            return totalingresos;
        }

        public void setTotalingresos(String totalingresos) {
            this.totalingresos = totalingresos;
        }

        public String getTotalapagar2() {
            return totalapagar2;
        }

        public void setTotalapagar2(String totalapagar2) {
            this.totalapagar2 = totalapagar2;
        }

        public String getTotalegresos2() {
            return totalegresos2;
        }

        public void setTotalegresos2(String totalegresos2) {
            this.totalegresos2 = totalegresos2;
        }

        public String getTotalingresos2() {
            return totalingresos2;
        }

        public void setTotalingresos2(String totalingresos2) {
            this.totalingresos2 = totalingresos2;
        }
    }

    public class DatosUtilidades2 {
        private int bloque;
        private String descripcionmovimiento;
        private int empresa;
        private String fechanomina;
        private String importe;
        private String importe2;
        private int numemp;
        private int orden;
        private String percepciondeduccion;
        private int tipomovimiento;

        public int getBloque() {
            return bloque;
        }

        public void setBloque(int bloque) {
            this.bloque = bloque;
        }

        public String getDescripcionmovimiento() {
            return descripcionmovimiento;
        }

        public void setDescripcionmovimiento(String descripcionmovimiento) {
            this.descripcionmovimiento = descripcionmovimiento;
        }

        public int getEmpresa() {
            return empresa;
        }

        public void setEmpresa(int empresa) {
            this.empresa = empresa;
        }

        public String getFechanomina() {
            return fechanomina;
        }

        public void setFechanomina(String fechanomina) {
            this.fechanomina = fechanomina;
        }

        public String getImporte() {
            return importe;
        }

        public void setImporte(String importe) {
            this.importe = importe;
        }

        public int getNumemp() {
            return numemp;
        }

        public void setNumemp(int numemp) {
            this.numemp = numemp;
        }

        public int getOrden() {
            return orden;
        }

        public void setOrden(int orden) {
            this.orden = orden;
        }

        public String getPercepciondeduccion() {
            return percepciondeduccion;
        }

        public void setPercepciondeduccion(String percepciondeduccion) {
            this.percepciondeduccion = percepciondeduccion;
        }

        public int getTipomovimiento() {
            return tipomovimiento;
        }

        public void setTipomovimiento(int tipomovimiento) {
            this.tipomovimiento = tipomovimiento;
        }

        public String getImporte2() {
            return importe2;
        }

        public void setImporte2(String importe2) {
            this.importe2 = importe2;
        }
    }
}
