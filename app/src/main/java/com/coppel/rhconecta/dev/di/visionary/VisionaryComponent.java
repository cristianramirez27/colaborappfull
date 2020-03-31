package com.coppel.rhconecta.dev.di.visionary;

import com.coppel.rhconecta.dev.presentation.visionary_detail.VisionaryDetailActivity;

import dagger.Component;

@Component(modules = { VisionaryModule.class })
public interface VisionaryComponent {

    /**
     *
     * @param visionaryDetailActivity
     */
    void inject(VisionaryDetailActivity visionaryDetailActivity);

}
