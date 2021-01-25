package com.coppel.rhconecta.dev.di.analytics;

import com.coppel.rhconecta.dev.presentation.splash.SplashScreenActivity;
import com.coppel.rhconecta.dev.views.activities.HomeActivity;

import dagger.Component;

/* */
@Component(modules = { AnalyticsModule.class })
public interface AnalyticsComponent {

    /**
     *
     */
    void inject(HomeActivity homeActivity);

    /**
     *
     */
    void injectSplash(SplashScreenActivity splashScreenActivity);

}
