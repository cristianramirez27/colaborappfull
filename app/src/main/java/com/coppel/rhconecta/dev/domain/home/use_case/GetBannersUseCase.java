package com.coppel.rhconecta.dev.domain.home.use_case;

import com.coppel.rhconecta.dev.data.home.HomeRepository;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class GetBannersUseCase extends UseCase<List<Banner>, UseCase.None> {

    /* */
    @Inject HomeRepository homeRepository;

    /**
     *
     */
    @Inject public GetBannersUseCase() { }

    /**
     *
     */
    @Override
    public void run(None none, OnResultFunction<Either <Failure, List<Banner>>> callback) {
        homeRepository.getBanners(callback);
    }

}
