package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType;

import java.io.Serializable;

public class WithDrawSavingRequestData implements Serializable{

    private WithDrawSavingType withDrawSavingType;
    private int opcion;
    private String num_empleado;

    private int imp_margencredito;
    private int imp_ahorroadicional;
    private int imp_cuotaahorro;

    private int clv_abonar;

    private int imp_fondoempleado;
    private int imp_cuentacorriente;
    private int clv_retiro;



    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, String num_empleado) {
        this.withDrawSavingType = withDrawSavingType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
    }


    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, String num_empleado, int imp_margencredito, int imp_ahorroadicional) {
        this.withDrawSavingType = withDrawSavingType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
        this.imp_margencredito = imp_margencredito;
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, int clv_abonar) {
        this.withDrawSavingType = withDrawSavingType;
        this.opcion = opcion;
        this.clv_abonar = clv_abonar;
    }

    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, String num_empleado, int clv_abonar) {
        this.withDrawSavingType = withDrawSavingType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
        this.clv_abonar = clv_abonar;
    }

    public WithDrawSavingType getWithDrawSavingType() {
        return withDrawSavingType;
    }

    public void setWithDrawSavingType(WithDrawSavingType withDrawSavingType) {
        this.withDrawSavingType = withDrawSavingType;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }


    public int getImp_margencredito() {
        return imp_margencredito;
    }

    public void setImp_margencredito(int imp_margencredito) {
        this.imp_margencredito = imp_margencredito;
    }

    public int getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(int imp_ahorroadicional) {
        this.imp_ahorroadicional = imp_ahorroadicional;
    }

    public int getImp_cuotaahorro() {
        return imp_cuotaahorro;
    }

    public void setImp_cuotaahorro(int imp_cuotaahorro) {
        this.imp_cuotaahorro = imp_cuotaahorro;
    }

    public int getClv_abonar() {
        return clv_abonar;
    }

    public void setClv_abonar(int clv_abonar) {
        this.clv_abonar = clv_abonar;
    }

    public int getImp_fondoempleado() {
        return imp_fondoempleado;
    }

    public void setImp_fondoempleado(int imp_fondoempleado) {
        this.imp_fondoempleado = imp_fondoempleado;
    }

    public int getImp_cuentacorriente() {
        return imp_cuentacorriente;
    }

    public void setImp_cuentacorriente(int imp_cuentacorriente) {
        this.imp_cuentacorriente = imp_cuentacorriente;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }
}
