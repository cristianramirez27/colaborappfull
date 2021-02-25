package com.coppel.rhconecta.dev.data.fingerprint

import android.content.Context
import com.coppel.rhconecta.dev.business.utils.ServicesConstants
import com.coppel.rhconecta.dev.data.common.messageOrClassName
import com.coppel.rhconecta.dev.data.common.retrofitApiCall
import com.coppel.rhconecta.dev.data.fingerprint.model.GetFingerprintsRequest
import com.coppel.rhconecta.dev.data.fingerprint.model.HaveFingerprintsRequest
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.fingerprint.FingerprintRepository
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsFailure
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.get_fingerprints.GetFingerprintsResponse
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsFailure
import com.coppel.rhconecta.dev.domain.fingerprint.use_case.have_fingerprints.HaveFingerprintsResponse
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities

/** */
class FingerprintRepositoryImpl(
        private val context: Context,
        private val fingerprintApiService: FingerprintApiService,
) : FingerprintRepository {

    /** */
    override suspend fun haveFingerprints(): Either<Failure, HaveFingerprintsResponse> =
            try {
                val authHeader: String = getAuthHeader()
                val request = HaveFingerprintsRequest(getEmployeeNumber())
                val url = ServicesConstants.GET_HUELLAS_ADICIONALES
                retrofitApiCall {
                    fingerprintApiService.haveFingerprints(authHeader, url, request)
                }.let {
                    val haveFingerprints = it.data.response.haveFingerprints()
                    val response = HaveFingerprintsResponse(haveFingerprints)
                    Either<Failure, HaveFingerprintsResponse>().Right(response)
                }
            } catch (exception: Exception) {
                val failure: Failure =
                        HaveFingerprintsFailure(exception.messageOrClassName())
                Either<Failure, HaveFingerprintsResponse>().Left(failure)
            }

    /** */
    override suspend fun getFingerprints(): Either<Failure, GetFingerprintsResponse> =
            try {
                val authHeader: String = getAuthHeader()
                val request = GetFingerprintsRequest(getEmployeeNumber())
                val url = ServicesConstants.GET_HUELLAS_ADICIONALES
                retrofitApiCall {
                    fingerprintApiService.getFingerprints(authHeader, url, request)
                }.let {
                    val fingerprints = it.data.response.fingerprintsDto.map { dto ->
                        dto.toFingerprint()
                    }
                    val response = GetFingerprintsResponse(fingerprints)
                    Either<Failure, GetFingerprintsResponse>().Right(response)
                }
            } catch (exception: Exception) {
                val failure: Failure =
                        GetFingerprintsFailure(exception.messageOrClassName())
                Either<Failure, GetFingerprintsResponse>().Left(failure)
            }

    /** */
    private fun getEmployeeNumber(): Long =
            AppUtilities.getStringFromSharedPreferences(
                    context,
                    AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR
            ).toLong()

    /** */
    private fun getAuthHeader(): String =
            AppUtilities.getStringFromSharedPreferences(
                    context,
                    AppConstants.SHARED_PREFERENCES_TOKEN
            )

}