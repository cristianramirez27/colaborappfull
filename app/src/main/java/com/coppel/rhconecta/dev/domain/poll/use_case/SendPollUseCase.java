package com.coppel.rhconecta.dev.domain.poll.use_case;

import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.PollRepository;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;

import javax.inject.Inject;

/**
 *
 *
 */
public class SendPollUseCase extends UseCase<UseCase.None, SendPollUseCase.Params> {

    /* */
    @Inject
    public PollRepository pollRepository;

    /**
     *
     *
     */
    @Inject
    public SendPollUseCase() { /* PASS */ }

    /**
     *
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, None>> callback) {
        pollRepository.sendPoll(params.poll, callback);
    }

    /**
     *
     *
     */
    public static class Params {

        /* */
        public Poll poll;

        /**
         *
         *
         */
        public Params(Poll poll) {
            this.poll = poll;
        }

    }

}
