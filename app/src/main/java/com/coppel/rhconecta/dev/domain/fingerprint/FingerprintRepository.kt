package com.coppel.rhconecta.dev.domain.fingerprint

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsResponse
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsResponse

/** */
interface FingerprintRepository {

    /** */
    suspend fun haveFingerprints(): Either<Failure, HaveFingerprintsResponse>

    /** */
    suspend fun getFingerprints(): Either<Failure, GetFingerprintsResponse>

}