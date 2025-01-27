package com.coppel.rhconecta.dev.domain.visionary.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import javax.inject.Inject;

/* */
public class UpdateVisionaryStatusUseCase extends UseCase<Visionary.RateStatus, UpdateVisionaryStatusUseCase.Params> {

    /* */
    @Inject
    VisionaryRepository visionaryRepository;

    /**
     *
     */
    public static class Params {

        /* */
        String visionaryId;

        /* */
        VisionaryType visionaryType;

        /* */
        Visionary.RateStatus status;

        /* */
        String rateOptionId;

        /**
         *
         */
        public Params(VisionaryType visionaryType, String visionaryId, Visionary.RateStatus status) {
            this.visionaryId = visionaryId;
            this.visionaryType = visionaryType;
            this.status = status;
            rateOptionId = null;
        }

        /**
         *
         */
        public Params(VisionaryType visionaryType, String visionaryId, Visionary.RateStatus status, String rateOptionId) {
            this.visionaryId = visionaryId;
            this.visionaryType = visionaryType;
            this.status = status;
            this.rateOptionId = rateOptionId;
        }

    }

    /* */
    @Inject
    UpdateVisionaryStatusUseCase() { /* Empty body */ }

    /**
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Visionary.RateStatus>> callback) {
        visionaryRepository.updateVisionaryStatusById(
                params.visionaryType,
                params.visionaryId,
                params.status,
                params.rateOptionId,
                callback
        );
    }

}
