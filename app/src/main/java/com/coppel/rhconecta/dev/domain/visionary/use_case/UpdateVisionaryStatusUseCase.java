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
public class UpdateVisionaryStatusUseCase extends UseCase<Visionary.Status, UpdateVisionaryStatusUseCase.Params> {

    /* */
    @Inject
    VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    public static class Params {

        /* */
        String visionaryId;
        /* */
        VisionaryType visionaryType;
        /* */
        Visionary.Status status;

        /**
         *
         *
         */
        public Params(VisionaryType visionaryType, String visionaryId, Visionary.Status status) {
            this.visionaryId = visionaryId;
            this.visionaryType = visionaryType;
            this.status = status;
        }
    }

    /**
     *
     *
     */
    @Inject UpdateVisionaryStatusUseCase() { }

    /**
     *
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Visionary.Status>> callback) {
        visionaryRepository.updateVisionaryStatusById(
                params.visionaryType,
                params.visionaryId,
                params.status,
                callback
        );
    }

}
