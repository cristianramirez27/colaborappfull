package com.coppel.rhconecta.dev.domain.home;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;

/**
 *
 *
 */
public interface HomeRepository {

    /**
     *
     * @return
     */
    void getBanners(
            UseCase.OnResultFunction<Either<Failure, List<Banner>>> callback
    );

}
