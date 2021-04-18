package com.coppel.rhconecta.dev.domain.visionary.use_case

import com.coppel.rhconecta.dev.business.Enums.AccessOption
import com.coppel.rhconecta.dev.domain.common.Either
import com.coppel.rhconecta.dev.domain.common.UseCase
import com.coppel.rhconecta.dev.domain.common.failure.Failure
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType
import javax.inject.Inject

/** */
class GetVisionaryPreviewUseCase @Inject constructor()
    : UseCase<VisionaryPreview, GetVisionaryPreviewUseCase.Params>() {

    /** */
    class Params(
            val type: VisionaryType,
            val visionaryId: String,
            val accessOption: AccessOption,
    )

    @Inject
    lateinit var visionaryRepository: VisionaryRepository

    /** */
    override fun run(params: Params?, callback: OnResultFunction<Either<Failure, VisionaryPreview>>?) {
        visionaryRepository.getVisionaryPreview(
                params?.type,
                params?.visionaryId,
                params?.accessOption,
                callback
        )
    }

}