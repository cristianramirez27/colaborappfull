package com.coppel.rhconecta.dev.business.utils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonHeadersInterceptor implements Interceptor {

    private String dateRequest;
    private String token;
    private String latitude;
    private String longitude;
    private String transactionId;

    public CommonHeadersInterceptor(String dateRequest, String token, String latitude, String longitude, String transactionId) {
        this.dateRequest = dateRequest;
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transactionId = transactionId;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithHeaders = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("X-Coppel-Date-Request", dateRequest)
                .header("Authorization", token)
                .header("X-Coppel-Latitude", latitude)
                .header("X-Coppel-Longitude", longitude)
                .header("X-Coppel-TransactionId", transactionId)
                .build();
        return chain.proceed(requestWithHeaders);
    }
}
