package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeLocalRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.LocalDataHelpDeskAvailability
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveLocalDataHelpDeskAvailabilityUseCase @Inject constructor(private val dispatcher: CoroutineDispatcher) :
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
        CoroutineScope(dispatcher).launch {
            callback.onResult(homeLocalRepository.saveDataHelpDeskAvailability(params))
        }
    }
}
