package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired
import javax.inject.Inject

class GetPersonalDataHelpDeskUseCase @Inject constructor() :
    UseCase<HelpDeskDataRequired, UseCase.None>() {

    /**
     * Instance for data repository
     */
    @Inject
    lateinit var homeLocalRepository: HomeLocalRepository

    override fun run(
        params: None,
        callback: OnResultFunction<Either<Failure, HelpDeskDataRequired>>,
    ) {
        homeLocalRepository.getPersonalInfo(callback)
    }
}
