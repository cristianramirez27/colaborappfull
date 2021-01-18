package com.coppel.rhconecta.dev.domain.analytics;

import com.coppel.rhconecta.dev.analytics.AnalyticsFlow;
import com.coppel.rhconecta.dev.domain.common.Either;
import com.coppel.rhconecta.dev.domain.common.UseCase;
import com.coppel.rhconecta.dev.domain.common.failure.Failure;

/* */
public interface AnalyticsRepository {

    /**
     *
     */
    void sendTimeByAnalyticsFlow(
            AnalyticsFlow analyticsFlow,
            long timeInSecondsByFlow,
            UseCase.OnResultFunction<Either<Failure, UseCase.None>> callback
    );

}
