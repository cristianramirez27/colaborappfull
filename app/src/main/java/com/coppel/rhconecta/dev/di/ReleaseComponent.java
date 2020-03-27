package com.coppel.rhconecta.dev.di;

import com.coppel.rhconecta.dev.presentation.release_detail.ReleaseDetailActivity;

import dagger.Component;

@Component(modules = { ReleaseModule.class })
public interface ReleaseComponent {

    /**
     *
     * @param releaseDetailActivity
     */
    void inject(ReleaseDetailActivity releaseDetailActivity);

}
