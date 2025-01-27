package com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.FingerprintRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** */
class HaveFingerprintsUseCase(
        private val fingerprintRepository: FingerprintRepository
) : UseCase<HaveFingerprintsResponse, HaveFingerprintsParams>() {

    /** */
    override fun run(
            params: HaveFingerprintsParams?,
            callback: OnResultFunction<Either<Failure, HaveFingerprintsResponse>>?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = fingerprintRepository.haveFingerprints()
            callback?.onResult(response)
        }
    }

}