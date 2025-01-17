package com.coppel.rhconecta.dev.data.fingerprint

import com.coppel.rhconecta.dev.data.fingerprint.model.GetFingerprintsHttpResponse
import com.coppel.rhconecta.dev.data.fingerprint.model.GetFingerprintsRequest
import com.coppel.rhconecta.dev.data.fingerprint.model.HaveFingerprintsHttpResponse
import com.coppel.rhconecta.dev.data.fingerprint.model.HaveFingerprintsRequest
import retrofit2.Response
import retrofit2.http.*

/** */
interface FingerprintApiService {

    /** */
    @Headers("Content-Type: application/json")
    @POST
    suspend fun haveFingerprints(
            @Header("Authorization") authHeader: String,
            @Header("X-Coppel-Date-Request") dateRequest: String ,
            @Header("X-Coppel-Latitude") latitude: String,
            @Header("X-Coppel-Longitude") longitude: String ,
            @Header("X-Coppel-TransactionId")transactionId:  String,
            @Url url: String,
            @Body request: HaveFingerprintsRequest
    ): Response<HaveFingerprintsHttpResponse>

    /** */
    @Headers("Content-Type: application/json")
    @POST
    suspend fun getFingerprints(
            @Header("Authorization") authHeader: String,
            @Header("X-Coppel-Date-Request") dateRequest: String ,
            @Header("X-Coppel-Latitude") latitude: String,
            @Header("X-Coppel-Longitude") longitude: String ,
            @Header("X-Coppel-TransactionId")transactionId:  String,
            @Url url: String,
            @Body request: GetFingerprintsRequest
    ): Response<GetFingerprintsHttpResponse>

}
