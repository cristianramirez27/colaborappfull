package com.coppel.rhconecta.dev.data.calculator

import com.coppel.rhconecta.dev.data.calculator.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface CalculatorApiService {
    @POST
    @Headers("Content-Type: application/json")
    suspend fun getInformationCalculator(
        @Header("Authorization") authHeader: String,
        @Header("X-Coppel-Date-Request") dateRequest: String ,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String ,
        @Header("X-Coppel-TransactionId")transactionId:  String,
        @Url url: String,
        @Body request: RequestCalculator
    ): Response<CalculatorResponse>

    @POST
    @Headers("Content-Type: application/json")
    suspend fun getListedWeek(
        @Header("Authorization") authHeader: String,
        @Header("X-Coppel-Date-Request") dateRequest: String ,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String ,
        @Header("X-Coppel-TransactionId")transactionId:  String,
        @Url url: String,
        @Body request: RequestListedWeek
    ): Response<WeekResponse>

    @POST
    @Headers("Content-Type: application/json")
    suspend fun scoreFlow(
        @Header("Authorization") authHeader: String,
        @Header("X-Coppel-Date-Request") dateRequest: String ,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String ,
        @Header("X-Coppel-TransactionId")transactionId:  String,
        @Url url: String,
        @Body request: ScoreFlow
    ): Response<ScoreResponse>

    @POST
    @Headers("Content-Type: application/json")
    suspend fun registerStep(
        @Header("Authorization") authHeader: String,
        @Header("X-Coppel-Date-Request") dateRequest: String ,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String ,
        @Header("X-Coppel-TransactionId")transactionId:  String,
        @Url url: String,
        @Body request: RegisterStep
    ): Response<StepResponse>

    @POST
    @Headers("Content-Type: application/json")
    suspend fun getResultCalculation(
        @Header("Authorization") authHeader: String,
        @Header("X-Coppel-Date-Request") dateRequest: String ,
        @Header("X-Coppel-Latitude") latitude: String,
        @Header("X-Coppel-Longitude") longitude: String ,
        @Header("X-Coppel-TransactionId")transactionId:  String,
        @Url url: String,
        @Body request: RequestCalculation
    ): Response<ResultCalculation>
}
