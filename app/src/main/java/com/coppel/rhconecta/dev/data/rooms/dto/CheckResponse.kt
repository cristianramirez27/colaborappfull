package com.coppel.rhconecta.dev.data.rooms.dto

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.google.gson.annotations.SerializedName

class CheckResponse(
    @SerializedName("data") val data: DataResponse
) : CoppelGeneralParameterResponse() {
    class DataResponse(val response: Response)
    class Response(
        val error: Int,
        val code: Int,
        val userMessage: String
    )
}
