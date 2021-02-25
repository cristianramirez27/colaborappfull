package com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints

import com.coppel.rhconecta.dev.domain.common.failure.Failure

/** */
data class GetFingerprintsFailure(val message: String) : Failure
