package com.coppel.rhconecta.dev.data.home

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase.OnResultFunction
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
    fun getPersonalInfo(
        callback: OnResultFunction<Either<Failure, HelpDeskDataRequired>>,
    )

    /**
     * Write in local the waiting time to check help desk availability (zendesk)
     */
    fun saveDataHelpDeskAvailability(
        callback: OnResultFunction<Either<Failure, Boolean>>,
        data: LocalDataHelpDeskAvailability,
    )

    /**
     * Get  the waiting time to check help desk availability (zendesk)
     */
    fun getDataHelpDeskAvailability(callback: OnResultFunction<Either<Failure, LocalDataHelpDeskAvailability>>)
}