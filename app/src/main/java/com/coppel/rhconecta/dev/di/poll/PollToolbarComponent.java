package com.coppel.rhconecta.dev.di.poll;

import com.coppel.rhconecta.dev.presentation.poll_toolbar.PollToolbarFragment;

import dagger.Component;

/**
 *
 *
 */
@Component(modules = { PollModule.class })
public interface PollToolbarComponent {

    /**
     *
     *
     */
    void inject(PollToolbarFragment pollToolbarFragment);

}
