package com.coppel.rhconecta.dev.domain.visionary.use_case

import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType
import javax.inject.Inject

/** */
class SendVisionaryActionUseCase @Inject constructor() : UseCase<Unit, SendVisionaryActionUseCase.Params>() {

    @Inject
    lateinit var visionaryRepository: VisionaryRepository

    /** */
    data class Params(
            val type: VisionaryType,
            val visionaryId: Long,
            val clvType: Int
    )

    /** */
    override fun run(params: Params, callback: OnResultFunction<Either<Failure, Unit>>?) {
        val (type, visionaryId, clvType) = params
        visionaryRepository.sendVisionaryAction(type, visionaryId, clvType, callback)
    }

}