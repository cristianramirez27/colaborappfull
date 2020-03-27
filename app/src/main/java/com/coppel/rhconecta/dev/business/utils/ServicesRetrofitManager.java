package com.coppel.rhconecta.dev.business.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicesRetrofitManager {

    private static ServicesRetrofitManager instance = null;
    private HttpLoggingInterceptor bodyInterceptor = new HttpLoggingInterceptor();

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(bodyInterceptor)
            .build();

    public synchronized static ServicesRetrofitManager getInstance() {
        if (instance == null) {
            instance = new ServicesRetrofitManager();
            instance.bodyInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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

}
