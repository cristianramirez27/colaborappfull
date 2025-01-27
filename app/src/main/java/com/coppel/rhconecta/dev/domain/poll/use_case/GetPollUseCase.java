package com.coppel.rhconecta.dev.domain.poll.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;
import com.coppel.rhconecta.dev.domain.poll.entity.Question;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 *
 *
 */
public class GetPollUseCase extends UseCase<Poll, UseCase.None> {

    @Inject
    public PollRepository pollRepository;

    /**
     *
     *
     */
    @Inject
    public GetPollUseCase() { /* PASS */ }

    /**
     *
     */
    @Override
    public void run(None none, OnResultFunction<Either<Failure, Poll>> callback) {
        pollRepository.getPoll(callback);
    }

}
