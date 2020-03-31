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
public class GetVisionaryUseCase extends UseCase<Visionary, GetVisionaryUseCase.Params> {

    /* */
    @Inject
    public VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    public static class Params {

        private String visionaryId;

        public Params(String visionaryId) {
        this.visionaryId = visionaryId;
    }

        public String getVisionaryId() {
        return visionaryId;
    }

    }

    /**
     *
     *
     */
    @Inject public GetVisionaryUseCase() { }

    /**
     *
     * @param params
     * @param callback
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Visionary>> callback) {
        visionaryRepository.getVisionaryById(params.getVisionaryId(), callback);
    }

}

