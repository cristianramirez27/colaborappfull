package com.coppel.rhconecta.dev.data.calculator.dto

import com.google.gson.annotations.SerializedName

data class RegisterStep(
    @SerializedName("nomEmpleado")
    val name: String,
    @SerializedName("numEmpleado")
    val number: String,
    @SerializedName("paso")
    val step: Int
)
