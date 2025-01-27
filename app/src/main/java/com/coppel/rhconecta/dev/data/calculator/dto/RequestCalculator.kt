package com.coppel.rhconecta.dev.data.calculator.dto

import com.google.gson.annotations.SerializedName

data class RequestCalculator(@SerializedName("num_empleado") val number: String, val opcion: Int = 1)
