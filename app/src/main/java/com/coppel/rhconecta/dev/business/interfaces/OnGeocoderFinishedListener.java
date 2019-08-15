package com.coppel.rhconecta.dev.business.interfaces;

import android.location.Address;

import java.util.List;

public interface OnGeocoderFinishedListener {
    public abstract void onFinished(List<Address> results);
}