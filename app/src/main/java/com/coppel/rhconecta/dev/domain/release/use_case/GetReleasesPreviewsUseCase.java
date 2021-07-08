package com.coppel.rhconecta.dev.domain.release.use_case;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;

import java.util.List;

import javax.inject.Inject;

/* */
public class GetReleasesPreviewsUseCase
        extends UseCase<List<ReleasePreview>, GetReleasesPreviewsUseCase.Params> {

    /* */
    @Inject
    public ReleaseRepository releaseRepository;

    /**
     *
     */
    public static class Params {

        private final AccessOption accessOption;

        public Params(AccessOption accessOption) {
            this.accessOption = accessOption;
        }

        public AccessOption getAccessOption() {
            return accessOption;
        }

    }

    /* */
    @Inject public GetReleasesPreviewsUseCase() { }

    /**
     *
     */
    @Override
    public void run(
            Params params,
            OnResultFunction<Either<Failure, List<ReleasePreview>>> callback
    ) {
        releaseRepository.getReleasesPreviews(params.accessOption, callback);
    }

}
