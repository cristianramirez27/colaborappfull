package com.coppel.rhconecta.dev.data.calculator.dto

import com.google.gson.annotations.SerializedName

data class RequestListedWeek(
    val nss: String,
    @SerializedName("antiguedad")
    val antiquity: String
) {
}