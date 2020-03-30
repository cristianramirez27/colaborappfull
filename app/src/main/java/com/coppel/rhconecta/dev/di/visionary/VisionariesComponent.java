package com.coppel.rhconecta.dev.di.visionary;

import com.coppel.rhconecta.dev.presentation.visionaries.VisionariesActivity;

import dagger.Component;

@Component(modules = { VisionariesModule.class })
public interface VisionariesComponent {

    /**
     *
     * @param visionariesActivity
     */
    void inject(VisionariesActivity visionariesActivity);

}
