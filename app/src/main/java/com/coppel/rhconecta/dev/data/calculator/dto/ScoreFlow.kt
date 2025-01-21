package com.coppel.rhconecta.dev.data.calculator.dto

import com.google.gson.annotations.SerializedName

data class ScoreFlow(
    @SerializedName("calificacion")
    val score: Int,
    @SerializedName("numEmpleado")
    val number: String,
    @SerializedName("queTeGusto")
    val like: String,
    @SerializedName("queCambiarias")
    val changed: String
)
