package com.coppel.rhconecta.dev.business.models;

public class ProfileResponse extends CoppelGeneralParameterResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        private Response[] response;

        public Response[] getResponse() {
            return response;
        }

        public void setResponse(Response[] response) {
            this.response = response;
        }
    }

    public class Response {
        private String Antiguedad;
        private String Centro;
        private int Ciudad;
        private String Colaborador;
        private String Curp;
        private int Empresa;
        private int Erh;
        private int EsBaja;
        private int EsGte;
        private int EsSuplente;
        private int Suplente;
        private int Estado;
        private String FechaIngreso;
        private int Gte;
        private String Nombre;
        private String NombreCentro;
        private String NombreCiudad;
        private String NombreColaborador;
        private String NombreEmpresa;
        private String NombreErh;
        private String NombreGte;
        private String NombrePuesto;
        private String NombreRegion;
        private String NombreSeccion;
        private String Nss;
        private String Puesto;
        private int Region;
        private String Rfc;
        private int Seccion;
        private String apellidom;
        private String apellidop;
        private int TipoNomina;
        private String correo;
        private String fechanacimiento;
        private String fotoperfil;
        private String idestado;
        private String sexo;
        private String errorCode;
        private String userMessage;

        public String getAntiguedad() {
            return Antiguedad;
        }

        public void setAntiguedad(String antiguedad) {
            Antiguedad = antiguedad;
        }

        public String getCentro() {
            return Centro;
        }

        public void setCentro(String centro) {
            Centro = centro;
        }

        public int getCiudad() {
            return Ciudad;
        }

        public void setCiudad(int ciudad) {
            Ciudad = ciudad;
        }

        public String getColaborador() {
            return Colaborador;
        }

        public void setColaborador(String colaborador) {
            Colaborador = colaborador;
        }

        public String getCurp() {
            return Curp;
        }

        public void setCurp(String curp) {
            Curp = curp;
        }

        public int getEmpresa() {
            return Empresa;
        }

        public void setEmpresa(int empresa) {
            Empresa = empresa;
        }

        public int getErh() {
            return Erh;
        }

        public void setErh(int erh) {
            Erh = erh;
        }

        public int getEsBaja() {
            return EsBaja;
        }

        public void setEsBaja(int esBaja) {
            EsBaja = esBaja;
        }

        public int getEsGte() {
            return EsGte;
        }

        public void setEsGte(int esGte) {
            EsGte = esGte;
        }

        public int getEstado() {
            return Estado;
        }

        public void setEstado(int estado) {
            Estado = estado;
        }

        public String getFechaIngreso() {
            return FechaIngreso;
        }

        public void setFechaIngreso(String fechaIngreso) {
            FechaIngreso = fechaIngreso;
        }

        public int getGte() {
            return Gte;
        }

        public void setGte(int gte) {
            Gte = gte;
        }

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }

        public String getNombreCentro() {
            return NombreCentro;
        }

        public void setNombreCentro(String nombreCentro) {
            NombreCentro = nombreCentro;
        }

        public String getNombreCiudad() {
            return NombreCiudad;
        }

        public void setNombreCiudad(String nombreCiudad) {
            NombreCiudad = nombreCiudad;
        }

        public String getNombreColaborador() {
            return NombreColaborador;
        }

        public void setNombreColaborador(String nombreColaborador) {
            NombreColaborador = nombreColaborador;
        }

        public String getNombreEmpresa() {
            return NombreEmpresa;
        }

        public void setNombreEmpresa(String nombreEmpresa) {
            NombreEmpresa = nombreEmpresa;
        }

        public String getNombreErh() {
            return NombreErh;
        }

        public void setNombreErh(String nombreErh) {
            NombreErh = nombreErh;
        }

        public String getNombreGte() {
            return NombreGte;
        }

        public void setNombreGte(String nombreGte) {
            NombreGte = nombreGte;
        }

        public String getNombrePuesto() {
            return NombrePuesto;
        }

        public void setNombrePuesto(String nombrePuesto) {
            NombrePuesto = nombrePuesto;
        }

        public String getNombreRegion() {
            return NombreRegion;
        }

        public void setNombreRegion(String nombreRegion) {
            NombreRegion = nombreRegion;
        }

        public String getNombreSeccion() {
            return NombreSeccion;
        }

        public void setNombreSeccion(String nombreSeccion) {
            NombreSeccion = nombreSeccion;
        }

        public String getNss() {
            return Nss;
        }

        public void setNss(String nss) {
            Nss = nss;
        }

        public String getPuesto() {
            return Puesto;
        }

        public void setPuesto(String puesto) {
            Puesto = puesto;
        }

        public int getRegion() {
            return Region;
        }

        public void setRegion(int region) {
            Region = region;
        }

        public String getRfc() {
            return Rfc;
        }

        public void setRfc(String rfc) {
            Rfc = rfc;
        }

        public int getSeccion() {
            return Seccion;
        }

        public void setSeccion(int seccion) {
            Seccion = seccion;
        }

        public String getApellidom() {
            return apellidom;
        }

        public void setApellidom(String apellidom) {
            this.apellidom = apellidom;
        }

        public String getApellidop() {
            return apellidop;
        }

        public void setApellidop(String apellidop) {
            this.apellidop = apellidop;
        }

        public String getFechanacimiento() {
            return fechanacimiento;
        }

        public void setFechanacimiento(String fechanacimiento) {
            this.fechanacimiento = fechanacimiento;
        }

        public String getIdestado() {
            return idestado;
        }

        public void setIdestado(String idestado) {
            this.idestado = idestado;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        public int getTipoNomina() {
            return TipoNomina;
        }

        public void setTipoNomina(int tipoNomina) {
            TipoNomina = tipoNomina;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getFotoperfil() {
            return fotoperfil;
        }

        public void setFotoperfil(String fotoperfil) {
            this.fotoperfil = fotoperfil;
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

        public int getEsSuplente() {
            return EsSuplente;
        }

        public void setEsSuplente(int esSuplente) {
            EsSuplente = esSuplente;
        }

        public int getSuplente() {
            return Suplente;
        }

        public void setSuplente(int suplente) {
            Suplente = suplente;
        }
    }
}
