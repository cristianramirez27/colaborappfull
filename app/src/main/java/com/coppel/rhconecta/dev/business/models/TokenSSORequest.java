package com.coppel.rhconecta.dev.business.models;

public class TokenSSORequest {
    private String email;
    private String password;
    private String num_empleado;
    private Integer opcion;
    private String recaptchaToken;
    public TokenSSORequest(){}

    public TokenSSORequest(String email, String password, String num_empleado,Integer opcion,String _recaptchaToken){
        this.email = email;
        this.password = password;
        this.num_empleado = num_empleado;
        this.opcion = opcion;
        this.recaptchaToken = _recaptchaToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public void setTokenCatcha(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
    }

    public String getrecaptchaToken() {
        return recaptchaToken;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }
}
