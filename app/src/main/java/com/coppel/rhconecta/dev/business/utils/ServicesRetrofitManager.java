package com.coppel.rhconecta.dev.business.utils;

import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.TIME_OUT;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class ServicesRetrofitManager {

    /* */
    private static ServicesRetrofitManager instance = null;

    /* Enabled body http interceptor in DEBUG mode */
    private final HttpLoggingInterceptor bodyInterceptor = new HttpLoggingInterceptor();

    /**
     *
     */
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            /* Enabled body http interceptor in DEBUG mode */
            .addInterceptor(bodyInterceptor)
            .build();

    /**
     *
     */
    public synchronized static ServicesRetrofitManager getInstance() {
        if (instance == null) {
            instance = new ServicesRetrofitManager();
            /* Enabled body http interceptor in DEBUG mode */
            instance.bodyInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return instance;
    }

    /**
     *
     */
    public Retrofit getRetrofitAPI(){
        return new Retrofit.Builder()
                .baseUrl(ServicesConstants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
