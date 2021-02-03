package com.coppel.rhconecta.dev.di.visionary;

import com.coppel.rhconecta.dev.presentation.visionaries.VisionariesActivity;

import dagger.Component;

/* */
@Component(modules = { VisionariesModule.class })
public interface VisionariesComponent {

    /**
     *
     */
    void inject(VisionariesActivity visionariesActivity);

}
