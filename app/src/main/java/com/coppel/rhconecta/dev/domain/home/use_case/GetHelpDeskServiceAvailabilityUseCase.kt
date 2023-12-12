package com.coppel.rhconecta.dev.domain.home.use_case

import com.coppel.rhconecta.dev.data.home.HomeRepository
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability
import com.coppel.rhconecta.dev.domain.home.entity.HomeParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetHelpDeskServiceAvailabilityUseCase @Inject constructor(private val dispatcher: CoroutineDispatcher) :
    UseCase<HelpDeskAvailability, HomeParams>() {

    /**
     * Instance for data repository
     */
    @Inject
    lateinit var homeRepository: HomeRepository

    override fun run(
        params: HomeParams,
        callback: OnResultFunction<Either<Failure, HelpDeskAvailability>>,
    ) {
        CoroutineScope(dispatcher).launch {
            callback.onResult(
                homeRepository.getHelpDeskServiceAvailability(
                    params.employeeNum,
                    params.authHeader
                )
            )
        }
    }
}
