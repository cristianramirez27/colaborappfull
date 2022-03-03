package com.coppel.rhconecta.dev.data.common

import java.io.IOException
import java.net.UnknownHostException

fun Exception.getOnFailureResponse(): TypeError {
    return when (this) {
        is UnknownHostException -> {
            TypeError.ERROR_CONNECTION
        }
        is IOException -> {
            TypeError.ERROR_UNAVAILABLE
        }
        is IllegalStateException -> {
            TypeError.ERROR_BAD_FORMAT
        }
        else -> {
            TypeError.ERROR_UNKNOWN_ERROR
        }
    }
}

enum class TypeError {
    ERROR_CONNECTION, ERROR_UNAVAILABLE, ERROR_BAD_FORMAT, ERROR_UNKNOWN_ERROR
}
