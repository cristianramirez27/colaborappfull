package com.coppel.rhconecta.dev.di.home;

import com.coppel.rhconecta.dev.presentation.home.HomeMainFragment;

import dagger.Component;

/* */
@Component(modules = {HomeModule.class})
public interface HomeComponent {

    /**
     *
     */
    void inject(HomeMainFragment homeMainFragment);
}
