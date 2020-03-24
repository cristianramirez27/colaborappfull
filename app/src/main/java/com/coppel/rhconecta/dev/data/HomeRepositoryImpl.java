package com.coppel.rhconecta.dev.data;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.HomeRepository;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;

import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 */
public class HomeRepositoryImpl implements HomeRepository {

    /**
     *
     *
     */
    @Inject public HomeRepositoryImpl(){

    }

    @Override
    public Either<Failure, List<Banner>> getBanners(String employeeNum) {
        return null;
    }

}
