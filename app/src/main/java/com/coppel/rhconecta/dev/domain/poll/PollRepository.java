package com.coppel.rhconecta.dev.domain.poll;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;

/* */
public interface PollRepository {

    /**
     *
     *
     */
    void getPoll(
            UseCase.OnResultFunction<Either<Failure, Poll>> callback
    );

    /**
     *
     *
     */
    void sendPoll(
            Poll  poll,
            UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback
    );

    /**
     *
     *
     */
    void getAvailablePollCount(
            UseCase.OnResultFunction<Either<Failure, Integer>> callback
    );

}
