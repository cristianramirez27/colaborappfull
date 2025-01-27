package com.coppel.rhconecta.dev.business.Enums;

import java.io.Serializable;

/**
 * Created by flima on 31/03/2017.
 */

public enum PushType implements Serializable {
    NOINVASIVE(1),
    INVASIVE(2);

    private int id;

    PushType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
