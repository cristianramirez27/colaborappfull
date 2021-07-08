package com.coppel.rhconecta.dev.domain.release.use_case;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
import com.coppel.rhconecta.dev.domain.release.entity.Release;

import javax.inject.Inject;

/* */
public class GetReleaseUseCase extends UseCase<Release, GetReleaseUseCase.Params> {

    /* */
    @Inject
    ReleaseRepository releaseRepository;

    /**
     *
     */
    public static class Params {

        private final int releaseId;
        private final AccessOption accessOption;

        public Params(int releaseId, AccessOption accessOption) {
            this.releaseId = releaseId;
            this.accessOption = accessOption;
        }

        public int getReleaseId() {
            return releaseId;
        }

        public AccessOption getAccessOption() {
            return accessOption;
        }

    }

    /**
     *
     */
    @Inject public GetReleaseUseCase() { }

    /**
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, Release>> callback) {
        releaseRepository.getReleaseById(
                params.getReleaseId(),
                params.getAccessOption(),
                callback
        );
    }

}
