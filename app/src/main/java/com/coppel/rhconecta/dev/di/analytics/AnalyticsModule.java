package com.coppel.rhconecta.dev.di.analytics;

import com.coppel.rhconecta.dev.data.analytics.AnalyticsRepositoryImpl;
import com.coppel.rhconecta.dev.domain.analytics.AnalyticsRepository;

import dagger.Module;
import dagger.Provides;

/* */
@Module
public class AnalyticsModule {

    /**
     *
     */
    @Provides
    AnalyticsRepository provideAnalyticsRepository() {
        return new AnalyticsRepositoryImpl();
    }

}
