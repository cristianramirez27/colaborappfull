package com.coppel.rhconecta.dev.domain.home.entity

import java.io.Serializable

data class HelpDeskAvailability(
    /**
     * Help desk chat service message
     */
    val mensaje: String,
    /**
     * Date on which the service will be consumed again
     * Format: 2022-09-01
     */
    val fecha: String,
    /**
     * Date with the start or end time of the message
     * Format: 2022-09-01 19:00:00
     */
    val fechaHora: String,
    /**
     * Remaining time to consume the service again
     * Format: 00:44:52
     */
    val horas: String,
) : Serializable
