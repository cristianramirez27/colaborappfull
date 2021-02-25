package com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.FingerprintRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** */
class GetFingerprintsUseCase(
        private val fingerprintRepository: FingerprintRepository
) : UseCase<GetFingerprintsResponse, GetFingerprintsParams>() {

    /** */
    override fun run(
            params: GetFingerprintsParams?,
            callback: OnResultFunction<Either<Failure, GetFingerprintsResponse>>?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = fingerprintRepository.getFingerprints()
            callback?.onResult(response)
        }
    }

}