package com.coppel.rhconecta.dev.domain.visionary.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;

import javax.inject.Inject;

/**
 *
 *
 */
public class UpdateVisionaryStatusUseCase extends UseCase<Visionary.Status, UpdateVisionaryStatusUseCase.Params> {

    /* */
    @Inject
    public VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    public static class Params {

        String visionaryId;
        Visionary.Status status;

        public Params(String visionaryId, Visionary.Status status) {
            this.visionaryId = visionaryId;
            this.status = status;
        }
    }

    /**
     *
     *
     */
    @Inject public UpdateVisionaryStatusUseCase() { /* PASS */ }

    /**
     *
     * @param params
     * @param callback
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Visionary.Status>> callback) {
        visionaryRepository.updateVisionaryStatusById(
                params.visionaryId,
                params.status,
                callback
        );
    }

}
