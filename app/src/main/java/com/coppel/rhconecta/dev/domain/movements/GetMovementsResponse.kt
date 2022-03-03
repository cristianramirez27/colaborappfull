package com.coppel.rhconecta.dev.domain.movements

import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement


import java.io.Serializable

/** */
data class GetMovementsResponse(
    var code: String,
    val dataList: List<Movement>,
    var message: String
) : Serializable
