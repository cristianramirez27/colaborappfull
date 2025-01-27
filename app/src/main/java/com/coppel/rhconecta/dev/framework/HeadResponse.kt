package com.coppel.rhconecta.dev.framework

import com.coppel.rhconecta.dev.business.models.CoppelGeneralParameterResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Head response Service
 */

open class CoppelGeneralParameterResponse : Serializable {
    lateinit var meta: Meta
}

data class Meta(val status: String) : Serializable

open class HomeAPIResponse<T> : CoppelGeneralParameterResponse(), Serializable {
    @SerializedName(APIConstants.KEY_DATA)
    lateinit var data: DataResponse<T>
}

open class DataResponse<T>(
    @SerializedName(APIConstants.KEY_RESPONSE)
    val response: T
) : HomeAPIResponse<T>(), Serializable