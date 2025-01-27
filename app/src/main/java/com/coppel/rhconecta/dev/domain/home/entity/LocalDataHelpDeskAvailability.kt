package com.coppel.rhconecta.dev.domain.home.entity

import java.io.Serializable

data class LocalDataHelpDeskAvailability(
    /**
     * Help desk chat service message
     */
    val errorMessage: String?,
    /**
     * Date on which the service will be consumed again
     * Format: 2022-09-01
     */
    val expiredDateInMillis: String?,
) : Serializable
