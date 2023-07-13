package com.coppel.rhconecta.dev.data.rooms.dto

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.google.gson.annotations.SerializedName

data class ReservationResponse(
    @SerializedName("data") val data: DataResponse) : CoppelGeneralParameterResponse() {
    class DataResponse (val response : Response)
    class Response (
        val error : Int,
        @SerializedName("lista") val list : List<Reservation>,
        val code : Int,
        val userMessage: String)
}
