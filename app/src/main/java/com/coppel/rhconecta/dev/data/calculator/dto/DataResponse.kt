package com.coppel.rhconecta.dev.data.calculator.dto

import com.coppel.rhconecta.dev.domain.calculator.ResultInformation
import com.google.gson.annotations.SerializedName

data class DataResponse(val response: Response)
data class Response(
    @SerializedName("codigo")
    val code: Int,
    @SerializedName("respuesta")
    val reply: String,
    val error: Int,
    val userMessage: String
)

fun DataResponse.toResultInformation(): ResultInformation {
    return ResultInformation(this.response.code, this.response.reply)
}

