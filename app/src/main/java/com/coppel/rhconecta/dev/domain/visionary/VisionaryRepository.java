package com.coppel.rhconecta.dev.domain.visionary;

import com.coppel.rhconecta.dev.business.Enums.AccessOption;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.entity.Visionary;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;
import com.coppel.rhconecta.dev.presentation.visionaries.VisionaryType;

import java.util.List;

/* */
public interface VisionaryRepository {

    /**
     *
     */
    void getVisionariesPreviews(
            VisionaryType type,
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback
    );

    /**
     *
     */
    void getVisionaryById(
            VisionaryType type,
            String visionaryId,
            AccessOption accessOption,
            UseCase.OnResultFunction<Either<Failure, Visionary>> callback
    );

    /**
     *
     */
    void updateVisionaryStatusById(
            VisionaryType visionaryType,
            String visionaryId,
            Visionary.Status status,
            UseCase.OnResultFunction<Either<Failure, Visionary.Status>> callback
    );

}
