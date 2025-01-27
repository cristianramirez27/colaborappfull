package com.coppel.rhconecta.dev.data.home

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired

/**
 *
 */
interface HomeLocalRepository {
    /**
     * Get the user information to be able to initialize zendesk
     */
    suspend fun getPersonalInfo(): Either<Failure, HelpDeskDataRequired>

    /**
     * Write in local the waiting time to check help desk availability (zendesk)
     */
    suspend fun saveDataHelpDeskAvailability(data: LocalDataHelpDeskAvailability): Either<Failure, Boolean>

    /**
     * Get  the waiting time to check help desk availability (zendesk)
     */
    suspend fun getDataHelpDeskAvailability(): Either<Failure, LocalDataHelpDeskAvailability>
}