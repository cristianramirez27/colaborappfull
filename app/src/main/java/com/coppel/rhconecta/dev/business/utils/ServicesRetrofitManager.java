package com.coppel.rhconecta.dev.business.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicesRetrofitManager {

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();

    private static ServicesRetrofitManager instance = null;

    public synchronized static ServicesRetrofitManager getInstance() {
        if (instance == null) {
            instance = new ServicesRetrofitManager();
        }
        return instance;
    }

    public Retrofit getRetrofitAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServicesConstants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public Retrofit getRetrofitAPI2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
