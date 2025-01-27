package com.coppel.rhconecta.dev.framework.home

import com.coppel.rhconecta.dev.business.Configuration.AppConfig.ANDROID_OS
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_CORREO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FECHA_HELP_DESK
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FECHA_HORA
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_HORAS
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_MESSAGE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_NUM_EMPLEADO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_OPTION_HOME
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_SO_DEVICE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_TOKEN
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Request object server
 * */
data class HomeRequest(
    @SerializedName(KEY_NUM_EMPLEADO) val employeeNumber: String,
    @SerializedName(KEY_CORREO) val correo: String,
    @SerializedName(KEY_TOKEN) val token: String,
    @SerializedName(KEY_OPTION_HOME) val option: Int,
    @SerializedName(KEY_SO_DEVICE) val deviceVersion: Int = ANDROID_OS,
) : Serializable

/**
 * Response object server
 */
class HelpDeskAvailabilityServer : Serializable {
    /**
     * Help desk chat service message
     */
    @SerializedName(KEY_MESSAGE)
    lateinit var mensaje: String

    /**
     * Date on which the service will be consumed again
     * Format: 2022-09-01
     */
    @SerializedName(KEY_FECHA_HELP_DESK)
    lateinit var fecha: String

    /**
     * Date with the start or end time of the message
     * Format: 2022-09-01 19:00:00
     */
    @SerializedName(KEY_FECHA_HORA)
    lateinit var fechaHora: String

    /**
     * Remaining time to consume the service again
     * Format: 00:44:52
     */
    @SerializedName(KEY_HORAS)
    lateinit var horas: String

}
