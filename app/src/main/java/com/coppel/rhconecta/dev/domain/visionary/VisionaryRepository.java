package com.coppel.rhconecta.dev.domain.visionary;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
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

}
