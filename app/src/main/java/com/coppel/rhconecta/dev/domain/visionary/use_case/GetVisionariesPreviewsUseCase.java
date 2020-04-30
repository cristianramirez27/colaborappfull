package com.coppel.rhconecta.dev.domain.visionary.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetVisionariesPreviewsUseCase extends UseCase<List<VisionaryPreview>, GetVisionariesPreviewsUseCase.Params> {

    /* */
    @Inject
    VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    @Inject GetVisionariesPreviewsUseCase() { }

    /**
     *
     *
     */
    @Override
    public void run(
            Params params,
            UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback
    ) {
        visionaryRepository.getVisionariesPreviews(params.type, callback);
    }

    /**
     *
     *
     */
    public static class Params {

        /* */
        public VisionaryType type;

        /**
         *
         *
         */
        public Params(VisionaryType type) {
            this.type = type;
        }

    }

}
