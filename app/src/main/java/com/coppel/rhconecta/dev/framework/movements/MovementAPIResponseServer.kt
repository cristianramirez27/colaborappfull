package com.coppel.rhconecta.dev.framework.movements

import com.coppel.rhconecta.dev.framework.APIConstants.KEY_CLV_CLAVE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_CONCEPTO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_DATA
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_DATOS
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_DES_MENSAJE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FECHA
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_FOLIO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_IMPORTE
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_NUM_EMPLEADO
import com.coppel.rhconecta.dev.framework.APIConstants.KEY_RESPONSE
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model backend service
 * */
data class GetMovementsRequest(
    @SerializedName(KEY_NUM_EMPLEADO) val employeeNumber: String
): Serializable

data class GetMovementsAPIResponse(
    @SerializedName(KEY_DATA) val data: Data
): Serializable

data class Data(
    @SerializedName(KEY_RESPONSE) val response: Response
): Serializable

data class Response(
    @SerializedName(KEY_CLV_CLAVE) val code: String,
    @SerializedName(KEY_DATOS) val dataList: List<MovementServer>,
    @SerializedName(KEY_DES_MENSAJE) val descriptionMessage: String
): Serializable

data class MovementServer(
    @SerializedName(KEY_CONCEPTO) val concept: String,
    @SerializedName(KEY_FECHA) val date: String,
    @SerializedName(KEY_FOLIO) val invoice: String,
    @SerializedName(KEY_IMPORTE) val amount: Double
): Serializable
