package com.coppel.rhconecta.dev.domain.release;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.release.entity.Release;
import com.coppel.rhconecta.dev.domain.release.entity.ReleasePreview;

import java.util.List;

public interface ReleaseRepository {

    /**
     *
     * @return
     */
    void getReleasesPreviews(
            UseCase.OnResultFunction<Either<Failure, List<ReleasePreview>>> callback
    );

    /**
     *
     * @return
     */
    void getReleaseById(
            int releaseId,
            UseCase.OnResultFunction<Either<Failure, Release>> callback
    );

}
