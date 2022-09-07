package com.coppel.rhconecta.dev.domain.home;

import com.coppel.rhconecta.dev.business.models.ZendeskResponse;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.BaseResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.entity.Badge;
import com.coppel.rhconecta.dev.domain.home.entity.Banner;
import com.coppel.rhconecta.dev.domain.home.entity.HelpDeskAvailability;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface HomeRepository {

    /**
     *
     */
    void getBanners(
            UseCase.OnResultFunction<Either<Failure, List<Banner>>> callback
    );

    /**
     *
     */
    void getBadges(
            UseCase.OnResultFunction<Either<Failure, Map<Badge.Type, Badge>>> callback
    );

    /**
     *
     */
    void getHelpDeskServiceAvailability(
            UseCase.OnResultFunction<Either<Failure, ZendeskResponse>> callback
    );

}
