package com.coppel.rhconecta.dev.di.poll;

import com.coppel.rhconecta.dev.presentation.poll.PollActivity;

import dagger.Component;

@Component(modules = { PollModule.class })
public interface PollComponent {

    /**
     *
     * @param pollActivity
     */
    void inject(PollActivity pollActivity);

}
