package com.coppel.rhconecta.dev.domain.release.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.ReleaseRepository;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetReleasesPreviewsUseCase extends UseCase<List<ReleasePreview>, UseCase.None> {

    @Inject
    ReleaseRepository releaseRepository;

    /**
     *
     *
     */
    @Inject public GetReleasesPreviewsUseCase() { }

    /**
     *
     * @param none
     * @param callback
     */
    @Override
    public void run(None none, OnResultFunction<Either<Failure, List<ReleasePreview>>> callback) {
        releaseRepository.getReleasesPreviews(callback);
    }

}
