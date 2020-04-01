package com.coppel.rhconecta.dev.domain.visionary;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;

import java.util.List;

public interface VisionaryRepository {

    /**
     *
     * @param callback
     */
    void getVisionariesPreviews(
            UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback
    );

    /**
     *
     *
     * @param visionaryId
     * @param callback
     */
    void getVisionaryById(
            String visionaryId,
            UseCase.OnResultFunction<Either<Failure, Visionary>> callback
    );

    /**
     *
     *
     * @param visionaryId
     * @param status
     * @param callback
     */
    void updateVisionaryStatusById(
            String visionaryId,
            Visionary.Status status,
            UseCase.OnResultFunction<Either<Failure, Visionary.Status>> callback
    );

}
