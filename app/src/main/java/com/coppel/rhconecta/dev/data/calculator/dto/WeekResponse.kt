package com.coppel.rhconecta.dev.data.calculator.dto

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.google.gson.annotations.SerializedName

data class WeekResponse(val data: DataResponse) : CoppelGeneralParameterResponse() {
    class DataResponse(val response: Response)
    class Response(
        @SerializedName("semanasCotizadas")
        val weeks: Int,
        val error: Int,
        val code: Int,
        val userMessage: String
    )
}
