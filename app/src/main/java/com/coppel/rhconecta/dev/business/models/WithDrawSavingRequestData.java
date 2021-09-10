package com.coppel.rhconecta.dev.business.models;

import com.coppel.rhconecta.dev.business.Enums.WithDrawSavingType;

import java.io.Serializable;

public class WithDrawSavingRequestData implements Serializable{

    private WithDrawSavingType withDrawSavingType;
    private int opcion;
    private String num_empleado;

    private Double imp_margencredito = 0.0;
    private Double imp_ahorroadicional = 0.0;
    private int imp_cuotaahorro;

    private int clv_abonar;

    private Double imp_fondoempleado = 0.0;
    private Double imp_cuentacorriente = 0.0;
    private int clv_retiro;

    //Se agrega parametro 07/07/19
    private int idu_traspaso;


    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, String num_empleado) {
        this.withDrawSavingType = withDrawSavingType;
        this.opcion = opcion;
        this.num_empleado = num_empleado;
    }


    public WithDrawSavingRequestData(WithDrawSavingType withDrawSavingType, int opcion, String num_empleado, Double imp_margencredito, Double imp_ahorroadicional) {
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


    public Double getImp_margencredito() {
        return imp_margencredito;
    }

    public void setImp_margencredito(Double imp_margencredito) {
        this.imp_margencredito = imp_margencredito;
    }

    public Double getImp_ahorroadicional() {
        return imp_ahorroadicional;
    }

    public void setImp_ahorroadicional(Double imp_ahorroadicional) {
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

    public Double getImp_fondoempleado() {
        return imp_fondoempleado;
    }

    public void setImp_fondoempleado(Double imp_fondoempleado) {
        this.imp_fondoempleado = imp_fondoempleado;
    }

    public Double getImp_cuentacorriente() {
        return imp_cuentacorriente;
    }

    public void setImp_cuentacorriente(Double imp_cuentacorriente) {
        this.imp_cuentacorriente = imp_cuentacorriente;
    }

    public int getClv_retiro() {
        return clv_retiro;
    }

    public void setClv_retiro(int clv_retiro) {
        this.clv_retiro = clv_retiro;
    }


    public int getIdu_traspaso() {
        return idu_traspaso;
    }

    public void setIdu_traspaso(int idu_traspaso) {
        this.idu_traspaso = idu_traspaso;
    }
}
