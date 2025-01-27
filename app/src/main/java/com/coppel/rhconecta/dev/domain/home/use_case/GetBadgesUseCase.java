package com.coppel.rhconecta.dev.domain.home.use_case;

import com.coppel.rhconecta.dev.data.home.HomeRepository;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;

import java.util.Map;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetBadgesUseCase extends UseCase<Map<Badge.Type, Badge>, UseCase.None> {

    /* */
    @Inject
    HomeRepository homeRepository;


    /**
     *
     *
     */
    @Inject
    public GetBadgesUseCase() { }

    /**
     *
     *
     */
    @Override
    public void run(None none, OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>> callback) {
        homeRepository.getBadges(callback);
    }
}
