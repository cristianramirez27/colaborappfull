package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Request;

public class JSON_GuardarLogin {

    private String aplicacionkey;
    private String numeroempleado;
    private String nombre;
    private String apellidop;
    private String apellidom;
    private int estatus;
    private int numerocentro;
    private int sexo;
    private String fechanacimiento;
    private String fechaalta;
    private String descripcionpuesto;
    private int idestado;
    private String estado;

    public JSON_GuardarLogin(String aplicacionkey, String numeroempleado, String nombre, String apellidop, String apellidom, int estatus, int numerocentro, int sexo, String fechanacimiento, String fechaalta, String descripcionpuesto, int idestado, String estado) {
        this.aplicacionkey = aplicacionkey;
        this.numeroempleado = numeroempleado;
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.estatus = estatus;
        this.numerocentro = numerocentro;
        this.sexo = sexo;
        this.fechanacimiento = fechanacimiento;
        this.fechaalta = fechaalta;
        this.descripcionpuesto = descripcionpuesto;
        this.idestado = idestado;
        this.estado = estado;
    }

    public String getAplicacionkey() {
        return aplicacionkey;
    }

    public void setAplicacionkey(String aplicacionkey) {
        this.aplicacionkey = aplicacionkey;
    }

    public String getNumeroempleado() {
        return numeroempleado;
    }

    public void setNumeroempleado(String numeroempleado) {
        this.numeroempleado = numeroempleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getNumerocentro() {
        return numerocentro;
    }

    public void setNumerocentro(int numerocentro) {
        this.numerocentro = numerocentro;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(String fechaalta) {
        this.fechaalta = fechaalta;
    }

    public String getDescripcionpuesto() {
        return descripcionpuesto;
    }

    public void setDescripcionpuesto(String descripcionpuesto) {
        this.descripcionpuesto = descripcionpuesto;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
