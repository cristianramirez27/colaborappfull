package com.coppel.rhconecta.dev.framework

import com.coppel.rhconecta.dev.framework.movements.Response
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import com.coppel.rhconecta.dev.views.utils.TextUtilities

const val ERROR_BACKEND = 100

fun Response.toMovementsDomainList(): List<Movement> = dataList.map {
    it.run {
        Movement(
            concept = concept.trim(),
            date = date,
            invoice = invoice,
            //This is a client based request, this needs to be fixed in the backend,
            // but it was agreed that this should be implemented in the frontend
            amount = TextUtilities.getNumberInCurrencyFormat(amount / ERROR_BACKEND)
        )
    }
}
