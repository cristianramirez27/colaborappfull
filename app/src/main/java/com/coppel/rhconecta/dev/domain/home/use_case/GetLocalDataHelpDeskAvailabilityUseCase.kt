package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetLocalDataHelpDeskAvailabilityUseCase @Inject constructor() :
    UseCase<LocalDataHelpDeskAvailability, UseCase.None>() {

    /**
     * Instance for data repository
     */
    @Inject
    lateinit var homeLocalRepository: HomeLocalRepository

    override fun run(
        params: None,
        callback: OnResultFunction<Either<Failure, LocalDataHelpDeskAvailability>>,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.onResult(homeLocalRepository.getDataHelpDeskAvailability())
        }
    }
}
