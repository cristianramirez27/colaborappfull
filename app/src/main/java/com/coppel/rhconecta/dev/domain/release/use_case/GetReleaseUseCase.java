package com.coppel.rhconecta.dev.domain.release.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
import com.coppel.rhconecta.dev.domain.release.entity.Release;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetReleaseUseCase extends UseCase<Release, GetReleaseUseCase.Params> {

    /* */
    @Inject
    ReleaseRepository releaseRepository;

    /**
     *
     *
     */
    public static class Params {

        private int releaseId;

        public Params(int releaseId) {
            this.releaseId = releaseId;
        }

        public int getReleaseId() {
            return releaseId;
        }

    }

    /**
     *
     *
     */
    @Inject public GetReleaseUseCase() { }

    /**
     *
     * @param params
     * @param callback
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Release>> callback) {
        releaseRepository.getReleaseById(params.getReleaseId(), callback);
    }

}
