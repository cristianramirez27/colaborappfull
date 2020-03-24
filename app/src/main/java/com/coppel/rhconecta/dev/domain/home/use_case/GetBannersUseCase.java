package com.coppel.rhconecta.dev.domain.home.use_case;

import android.util.Log;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.HomeRepository;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetBannersUseCase extends UseCase<List<Banner>, GetBannersUseCase.Params> {

    /* */
    @Inject HomeRepository homeRepository;


    /**
     *
     *
     */
    @Inject public GetBannersUseCase() { }

    /**
     *
     *
     */
    public static class Params {

        /* */
        String employeeNum;

        public Params(String employeeNum) {
            this.employeeNum = employeeNum;
        }
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    public Either<Failure, List<Banner>> run(GetBannersUseCase.Params params) {
        return homeRepository.getBanners(params.employeeNum);
    }

}
