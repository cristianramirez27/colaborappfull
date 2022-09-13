package com.coppel.rhconecta.dev.domain.home.entity

import java.io.Serializable

data class HelpDeskAvailability(
    val mensaje: String,
    val fecha: String,
    val fechaHora: String,
    val horas: String
) : Serializable
