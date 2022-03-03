package com.coppel.rhconecta.dev.framework

import com.coppel.rhconecta.dev.framework.movements.Response
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import com.coppel.rhconecta.dev.views.utils.TextUtilities

fun Response.toMovementsDomainList(): List<Movement> = dataList.map {
    it.run {
        Movement(
            concept = concept.trim(),
            date = date,
            invoice = invoice,
            amount = TextUtilities.getNumberInCurrencyFormat(amount)
        )
    }
}