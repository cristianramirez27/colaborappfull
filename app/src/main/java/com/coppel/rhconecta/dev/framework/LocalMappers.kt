package com.coppel.rhconecta.dev.framework

import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.framework.home.HelpDeskAvailabilityServer
import com.coppel.rhconecta.dev.framework.movements.Response
import com.coppel.rhconecta.dev.views.fragments.fondoAhorro.movements.model.Movement
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired
import com.coppel.rhconecta.dev.views.utils.TextUtilities


fun ProfileResponse.Response.toHelpDeskDataRequired(): HelpDeskDataRequired {
    return HelpDeskDataRequired(
        employName = this.nombre,
        email = this.correo,
        employNumber = this.colaborador,
        jobName = this.nombrePuesto,
        department = this.centro,
        deviceName = "",
        versionAndroid = "",
        versionApp = ""
    )
}