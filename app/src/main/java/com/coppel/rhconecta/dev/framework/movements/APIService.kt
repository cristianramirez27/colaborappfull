package com.coppel.rhconecta.dev.framework.movements

import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @Headers("Content-Type: application/json")
    @POST
    suspend fun getMovements(
        @Url url: String,
        @Header("Authorization") token: String,
        @Header("X-Coppel-Date-Request") dateRequest: String,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String,
        @Header("X-Coppel-TransactionId") transactionId: String,
        @Body servicesRequest: GetMovementsRequest
    ): Response<GetMovementsAPIResponse>
}
