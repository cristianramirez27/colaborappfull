package com.coppel.rhconecta.dev.data.calculator.dto

import com.google.gson.annotations.SerializedName

data class RequestCalculation(
    @SerializedName("salarioBase")
    val baseSalary: Double,
    @SerializedName("fechaNacimiento")
    val dateBorn: String,
    @SerializedName("fechaIngresoCoppel")
    val dateStart: String,
    @SerializedName("saldoFondoAhorro")
    val savings: Double,
    @SerializedName("saldoAportacionesOrdinarias")
    val contributionsOrdinary: Double,
    @SerializedName("saldoAportacionesVoluntarias")
    val contributionsVoluntary: Double,
    @SerializedName("saldoInicialAER1")
    val aer: Double,
    @SerializedName("saldoInicialAER2")
    val aer2: Double,
    @SerializedName("montoInicialMesesAcumulados")
    val initialMonths: Double,
    @SerializedName("esDirectivo")
    val directory: Int,
    val nss: String,
    @SerializedName("salarioBaseCotizacion")
    val salaryBaseContribution: Double,
    @SerializedName("prcAportacionVoluntaria")
    val voluntaryContribution: Int,
    @SerializedName("edadRetiro")
    val retirementAge: Int,
    @SerializedName("subcuentaVivienda")
    val infonavit: Double,
    @SerializedName("semanasCotizadas")
    val weeks: Int,
    @SerializedName("saldoAcumuladoAfore")
    val afore: Double,
    @SerializedName("saldoAhorroVoluntarioAfore")
    val aforeVoluntary: Double

)
