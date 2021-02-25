package com.coppel.rhconecta.dev.data.fingerprint.model

import com.coppel.rhconecta.dev.data.fingerprint.model.dto.FingerprintDto
import com.google.gson.annotations.SerializedName

/** */
data class GetFingerprintsRequest(
        @SerializedName("num_empleado") val employeeNumber: Long,
        @SerializedName("clv_opcion") val clvOption: Int = 2,
)

/** */
data class GetFingerprintsHttpResponse(
        @SerializedName("data") val data: GetFingerprintsData,
)

/** */
data class GetFingerprintsData(
        @SerializedName("response") val response: GetFingerprintsDataResponse,
)

/** */
data class GetFingerprintsDataResponse(
        @SerializedName("clv_estado") val clvState: String,
        @SerializedName("des_mensaje") val desMessage: String,
        @SerializedName("des_parentesco") val fingerprintsDto: List<FingerprintDto>,
)
