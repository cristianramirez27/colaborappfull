package com.coppel.rhconecta.dev.domain.poll.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;

import javax.inject.Inject;

public class GetAvailablePollCountUseCase extends UseCase<Integer, UseCase.None> {

    @Inject
    public PollRepository pollRepository;

    /**
     *
     *
     */
    @Inject
    public GetAvailablePollCountUseCase() { /* PASS */ }

    /**
     *
     */
    @Override
    public void run(None none, OnResultFunction<Either<Failure, Integer>> callback) {
        pollRepository.getAvailablePollCount(callback);
    }

}
