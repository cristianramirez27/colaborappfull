package com.coppel.rhconecta.dev.domain.home;

import com.coppel.rhconecta.dev.domain.common.Either;
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
     * @param employeeNum
     * @return
     */
    Either<Failure, List<Banner>> getBanners(String employeeNum);

}
