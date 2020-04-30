package com.coppel.rhconecta.dev.domain.visionary.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetVisionaryUseCase extends UseCase<Visionary, GetVisionaryUseCase.Params> {

    /* */
    @Inject
    VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    public static class Params {

        /* */
        VisionaryType visionaryType;
        /* */
        String visionaryId;

        /**
         *
         *
         */
        public Params(VisionaryType visionaryType, String visionaryId) {
            this.visionaryType = visionaryType;
            this.visionaryId = visionaryId;
        }

    }

    /**
     *
     *
     */
    @Inject GetVisionaryUseCase() { }

    /**
     *
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Visionary>> callback) {
        visionaryRepository.getVisionaryById(
                params.visionaryType,
                params.visionaryId,
                callback
        );
    }

}

