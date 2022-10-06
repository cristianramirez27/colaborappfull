package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import javax.inject.Inject

class SaveLocalDataHelpDeskAvailabilityUseCase @Inject constructor() :
    UseCase<Boolean, LocalDataHelpDeskAvailability>() {

    /**
     * Instance for data repository
     */
    @Inject
    lateinit var homeLocalRepository: HomeLocalRepository

    override fun run(
        params: LocalDataHelpDeskAvailability,
        callback: OnResultFunction<Either<Failure, Boolean>>,
    ) {
        homeLocalRepository.saveDataHelpDeskAvailability(callback, params)
    }
}
