package com.coppel.rhconecta.dev.domain.home.use_case;

import com.coppel.rhconecta.dev.business.models.ZendeskResponse;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.home.HomeRepository;

import javax.inject.Inject;

/* */
public class GetHelpDeskServiceAvailabilityUseCase extends UseCase<ZendeskResponse, UseCase.None>  {

    /* */
    @Inject
    HomeRepository homeRepository;

    /**
     *
     */
    @Inject
    public GetHelpDeskServiceAvailabilityUseCase() { /* Empty constructor */ }

    /**
     *
     */
    @Override
    public void run(None none, OnResultFunction<Either<Failure, ZendeskResponse>> callback) {
        homeRepository.getHelpDeskServiceAvailability(callback);
    }
}
