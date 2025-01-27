package com.coppel.rhconecta.dev.data.calculator.dto

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.google.gson.annotations.SerializedName

class CalculatorResponse(@SerializedName("data") val data: DataResponse) : CoppelGeneralParameterResponse() {
    class DataResponse(val response: Response)
    class Response(
        val Antiguedad: String,
        val FechaCorte: String,
        val FechaNacimiento: String,
        val MesesAcumulables: Int,
        val SalarioBase: Double,
        val TipoNomina: Int,
        val imp_aer: String,
        val imp_aer60: String,
        val imp_aportacionesadicionales: String,
        val imp_aportacionesordinarias: String,
        val imp_aportemensual: String,
        val imp_fondodeahorro: String,
        val imp_salariomensual: String,
        val error: Int,
        val code: Int,
        val userMessage: String
    )
}
