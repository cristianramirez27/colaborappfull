package com.coppel.rhconecta.dev.domain.home;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;
import java.util.Map;

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

    /**
     *
     * @return
     */
    void getBadges(
            UseCase.OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>> callback
    );

}
