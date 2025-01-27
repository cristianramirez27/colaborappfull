package com.coppel.rhconecta.dev.framework

import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.framework.home.HelpDeskAvailabilityServer
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

/**
 * User for help desk service (Zendesk)
 */
fun List<HelpDeskAvailabilityServer>.toHelpDeskAvailabilityDomain(): HelpDeskAvailability? =
    this.first()?.let { HelpDeskAvailability(it.mensaje, it.fecha, it.fechaHora, it.horas) }
