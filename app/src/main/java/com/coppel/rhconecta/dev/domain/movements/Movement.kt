package com.coppel.rhconecta.dev.domain.movements

import java.io.Serializable

data class Movement(
    val concept: String = "",
    val date: String = "",
    val invoice: String = "",
    val amount: Double = 0.0
) : Serializable
