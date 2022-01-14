package com.coppel.rhconecta.dev.business.models;

public class TokenSSORequest {
    private String email;
    private String password;
    private String num_empleado;

    public TokenSSORequest(){}

    public TokenSSORequest(String email, String password, String num_empleado){
        this.email = email;
        this.password = password;
        this.num_empleado = num_empleado;
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
}
