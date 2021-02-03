package com.coppel.rhconecta.dev.di.visionary;

import com.coppel.rhconecta.dev.presentation.visionary_detail.visionary_rate.VisionaryRateActivity;

import dagger.Component;

/* */
@Component(modules = { VisionaryRateModule.class })
public interface VisionaryRateComponent {

    /**
     *
     */
    void inject(VisionaryRateActivity visionaryRateActivity);

}
