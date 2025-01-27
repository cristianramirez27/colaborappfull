package com.coppel.rhconecta.dev.data.calculator.dto

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.coppel.rhconecta.dev.domain.calculator.CalculationResult
import com.google.gson.annotations.SerializedName

data class ResultCalculation(val data: DataResponse) : CoppelGeneralParameterResponse() {
    class DataResponse(val response: Response)

    class Response(
        @SerializedName("antiguedadRetiro")
        val antiquityRetirement: String,
        @SerializedName("aportacionOrdinaria")
        val contributionsOrdinary: String,
        @SerializedName("aportacionVoluntaria")
        val contributionsVoluntary: String,
        @SerializedName("cajaAhorro")
        val savingsBank: String,
        @SerializedName("cajaAhorroCapital")
        val capitalSavingsBank: String,
        @SerializedName("cajaAhorroInteres")
        val interestSavingsBank: String,
        @SerializedName("edadRetiro")
        val ageRetirement: String,
        @SerializedName("fondoAhorro")
        val savingsFund: String,
        @SerializedName("fondoAhorroCapital")
        val capitalSavingsFund: String,
        @SerializedName("fondoAhorroInteres")
        val interestSavingsFund: String,
        @SerializedName("pension3Meses")
        val pension: String,
        @SerializedName("prcIMSS")
        val imss: String,
        @SerializedName("prcRetiro")
        val percentRetirement: String,
        @SerializedName("saldoAER1")
        val aer: String,
        @SerializedName("saldoAER2")
        val aer2: String,
        @SerializedName("saldoAcumuladoPension")
        val balanceAccumulated: String,
        @SerializedName("semanasCotizadas")
        val weeks: String,
        val errorCode: Int,
        val userMessage: String
    )
}

fun ResultCalculation.Response.toMapCalculationResult(): CalculationResult {
    return CalculationResult(
        age = ageRetirement,
        antiquity = antiquityRetirement,
        weeks = weeks,
        savingsFundCapital = capitalSavingsFund,
        savingsFundInterest = interestSavingsFund,
        savingsFund = savingsFund,
        savingsBankCapital = capitalSavingsBank,
        savingsBankInterest = interestSavingsBank,
        savingsBank = savingsBank,
        ordinaryContribution = contributionsOrdinary,
        voluntaryContribution = contributionsVoluntary,
        aer = aer,
        aer2 = aer2,
        benefitCapital = pension,
        benefit = pension,
        percentImss = imss,
        percentRetirement = percentRetirement,
        pensionBalance = balanceAccumulated
    )
}
