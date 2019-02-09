package com.coppel.rhconecta.dev.business.models;

import java.io.Serializable;

/**
 * Created by faustolima on 29/11/18.
 */

public class StatesData<T> implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
