package com.coppel.rhconecta.dev.visionarios.videos.retrofit.GuardarLogAction;

import com.squareup.otto.Bus;

public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    public BusProvider() {
    }
}
