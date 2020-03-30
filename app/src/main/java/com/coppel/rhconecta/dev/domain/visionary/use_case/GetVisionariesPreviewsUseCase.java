package com.coppel.rhconecta.dev.domain.visionary.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.visionary.VisionaryRepository;
import com.coppel.rhconecta.dev.domain.visionary.entity.VisionaryPreview;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetVisionariesPreviewsUseCase extends UseCase<List<VisionaryPreview>, UseCase.None> {

    @Inject
    public VisionaryRepository visionaryRepository;

    /**
     *
     *
     */
    @Inject public GetVisionariesPreviewsUseCase() { }

    /**
     *
     * @param none
     * @param callback
     */
    @Override
    public void run(UseCase.None none, UseCase.OnResultFunction<Either<Failure, List<VisionaryPreview>>> callback) {
        visionaryRepository.getVisionariesPreviews(callback);
    }

}
