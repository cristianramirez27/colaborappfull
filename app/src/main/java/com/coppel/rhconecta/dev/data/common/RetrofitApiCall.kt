package com.coppel.rhconecta.dev.data.common

import retrofit2.HttpException
import retrofit2.Response

/** */
@Throws(HttpException::class, Exception::class)
suspend fun <T> retrofitApiCall(apiCall: suspend () -> Response<T>) : T {
    val response = apiCall()
    return if(response.isSuccessful)
        response.body()!!
    else throw HttpException(response)
}

/** */
fun Exception.messageOrClassName(): String =
    message ?: javaClass.simpleName
