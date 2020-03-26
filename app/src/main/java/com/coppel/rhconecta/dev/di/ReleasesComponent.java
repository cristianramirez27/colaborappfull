package com.coppel.rhconecta.dev.di;

import com.coppel.rhconecta.dev.presentation.releases.ReleasesActivity;

import dagger.Component;

/**
 *
 *
 */
@Component(modules = { ReleaseModule.class })
public interface ReleasesComponent {

    /**
     *
     * @param releasesActivity
     */
    void inject(ReleasesActivity releasesActivity);

}