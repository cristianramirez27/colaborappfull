package com.coppel.rhconecta.dev.domain.analytics.use_case;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.domain.analytics.AnalyticsRepository;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;
import com.coppel.rhconecta.dev.domain.poll.entity.Poll;

import javax.inject.Inject;

/* */
public class SendTimeByAnalyticsFlowUseCase
        extends UseCase<UseCase.None, SendTimeByAnalyticsFlowUseCase.Params> {

    /* */
    @Inject
    public AnalyticsRepository analyticsRepository;

    /**
     *
     */
    @Inject
    public SendTimeByAnalyticsFlowUseCase() { /* Empty constructor */ }

    /**
     *
     */
    @Override
    public void run(Params params, OnResultFunction<Either<Failure, None>> callback) {
        analyticsRepository.sendTimeByAnalyticsFlow(
                params.analyticsFlow,
                params.timeInSecondsByFlow,
                callback
        );
    }

    /**
     *
     */
    public static class Params {

        /* */
        public AnalyticsFlow analyticsFlow;

        /* */
        public long timeInSecondsByFlow;

        /**
         *
         */
        public Params(AnalyticsFlow analyticsFlow, long timeInSecondsByFlow) {
            this.analyticsFlow = analyticsFlow;
            this.timeInSecondsByFlow = timeInSecondsByFlow;
        }

    }

}
