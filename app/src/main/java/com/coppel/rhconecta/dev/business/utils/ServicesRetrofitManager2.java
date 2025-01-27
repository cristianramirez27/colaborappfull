package com.coppel.rhconecta.dev.business.utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServicesRetrofitManager2 {

    /*private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, String dateRequest, String token, String latitude, String longitude, String transactionId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CommonHeadersInterceptor(dateRequest, token, latitude, longitude, transactionId))
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/



}
