package com.coppel.rhconecta.dev.di;

import com.coppel.rhconecta.dev.views.fragments.HomeMainFragment;

import dagger.Component;

@Component
public interface DiComponent {

    /**
     *
     * @param homeMainFragment
     */
    void inject(HomeMainFragment homeMainFragment);

}
