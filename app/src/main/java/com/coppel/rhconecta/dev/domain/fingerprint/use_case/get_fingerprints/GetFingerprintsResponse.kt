package com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints

import com.coppel.rhconecta.dev.domain.fingerprint.entity.Fingerprint

/** */
data class GetFingerprintsResponse(
        val fingerprints: List<Fingerprint>
)
