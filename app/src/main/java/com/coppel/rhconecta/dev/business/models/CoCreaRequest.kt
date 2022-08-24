package com.coppel.rhconecta.dev.business.models

data class CoCreaRequest(val opcion: Int,
                         val email: String,
                         val password: String,
                         val app: String,
                         val version: String,
                         val so_dispositivo: Int)
