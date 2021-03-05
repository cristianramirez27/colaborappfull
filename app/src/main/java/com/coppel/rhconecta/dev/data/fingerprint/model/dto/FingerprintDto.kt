package com.coppel.rhconecta.dev.data.fingerprint.model.dto

import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint
import com.google.gson.annotations.SerializedName

/** */
data class FingerprintDto(
        @SerializedName("idu_adicional") val id: Int,
        @SerializedName("nom_adicional") val name: String,
        @SerializedName("des_parentesco") val relationship: String,
) {

    /** */
    fun toFingerprint(): Fingerprint =
            Fingerprint(id, name, relationship)

}