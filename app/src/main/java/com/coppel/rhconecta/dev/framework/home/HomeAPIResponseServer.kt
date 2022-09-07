package com.coppel.rhconecta.dev.framework.home

import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.business.Configuration.AppConfig.ANDROID_OS
import com.coppel.rhconecta.dev.business.models.CoppelServicesProfileRequest
import com.coppel.rhconecta.dev.business.utils.JsonManager
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_CORREO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_DATA
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_DATOS
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FECHA_HELP_DESK
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FECHA_HORA
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_HORAS
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_MESSAGE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_NUM_EMPLEADO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_OPTION_HOME
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_RESPONSE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_SO_DEVICE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_TOKEN
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model backend service
 * */
data class HomeRequest(
    @SerializedName(KEY_NUM_EMPLEADO) val employeeNumber: String,
    @SerializedName(KEY_CORREO) val correo: String,
    @SerializedName(KEY_TOKEN) val token: String,
    @SerializedName(KEY_OPTION_HOME) val option: Int,
    @SerializedName(KEY_SO_DEVICE) val deviceVersion: Int = ANDROID_OS
) : Serializable

fun buildProfileRequest(
    employeeNumber: String?,
    employeeEmail: String?,
    option: Int
): CoppelServicesProfileRequest? {
    val coppelServicesProfileRequest = CoppelServicesProfileRequest()
    coppelServicesProfileRequest.num_empleado = employeeNumber
    coppelServicesProfileRequest.correo = employeeEmail
    coppelServicesProfileRequest.opcion = option
    val tokenFirebase = AppUtilities.getStringFromSharedPreferences(
        CoppelApp.getContext(),
        AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN
    )
    if (tokenFirebase != null && !tokenFirebase.isEmpty()) {
        coppelServicesProfileRequest.id_firebase = tokenFirebase
    }
    val requestAsString = JsonManager.madeJsonFromObject(coppelServicesProfileRequest).toString()
    return coppelServicesProfileRequest
}

data class GetMovementsAPIResponse(
    @SerializedName(KEY_DATA) val data: Data
): Serializable

data class Data(
    @SerializedName(KEY_RESPONSE) val response: List<Response>
): Serializable

data class Response(
    @SerializedName(KEY_DATOS) val dataList: List<HelpDeskAvailabilityServer>,
): Serializable

data class HelpDeskAvailabilityServer(
       @SerializedName(KEY_MESSAGE) val mensaje: String,
       @SerializedName(KEY_FECHA_HELP_DESK) val fecha: String,
       @SerializedName(KEY_FECHA_HORA) val fechaHora: String,
       @SerializedName(KEY_HORAS) val horas: String
): Serializable
