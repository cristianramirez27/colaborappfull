package com.coppel.rhconecta.dev.business.models;

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.ANDROID_OS;

public class CoppelServicesProfileRequest {

    private String num_empleado;
    private String correo;
    private String id_firebase;
    private int opcion;
    private int so_dispositivo = ANDROID_OS;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String version;

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId_firebase() {
        return id_firebase;
    }

    public void setId_firebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }


    public int getSo_dispositivo() {
        return so_dispositivo;
    }

    public void setSo_dispositivo(int so_dispositivo) {
        this.so_dispositivo = so_dispositivo;
    }
}
