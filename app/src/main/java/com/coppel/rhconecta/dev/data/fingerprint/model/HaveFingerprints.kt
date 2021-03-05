package com.coppel.rhconecta.dev.data.fingerprint.model

import com.google.gson.annotations.SerializedName

/** */
data class HaveFingerprintsRequest(
        @SerializedName("num_empleado") val employeeNumber: Long,
        @SerializedName("clv_opcion") val clvOption: Int = 1,
)

/** */
data class HaveFingerprintsHttpResponse(
        @SerializedName("data") val data: HaveFingerprintsData,
)

/** */
data class HaveFingerprintsData(
        @SerializedName("response") val response: HaveFingerprintsDataResponse,
)

/** */
data class HaveFingerprintsDataResponse(
        @SerializedName("clv_estado") val clvState: String,
        @SerializedName("des_mensaje") val desMessage: String,
) {

    /** */
    fun haveFingerprints(): Boolean =
            clvState == "0"

}


