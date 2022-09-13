package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import javax.inject.Inject

class GetHelpDeskServiceAvailabilityUseCase @Inject constructor() :
    UseCase<HelpDeskAvailability, UseCase.None>() {

    /**
     * Instance for data repository
     */
    @Inject
    lateinit var homeRepository: HomeRepository

    override fun run(
        params: None,
        callback: OnResultFunction<Either<Failure, HelpDeskAvailability>>
    ) {
        homeRepository.getHelpDeskServiceAvailability(callback)
    }
}
