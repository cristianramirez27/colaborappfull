package com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints

import com.coppel.rhconecta.dev.domain.common.failure.Failure

/** */
data class HaveFingerprintsFailure(val message: String) : Failure
