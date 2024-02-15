package com.coppel.rhconecta.dev.business.utils;

import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.TIME_OUT;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[] {};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return false;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    public Retrofit getRetrofitAPI(){
        return new Retrofit.Builder()
                .baseUrl(ServicesConstants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(getUnsafeOkHttpClient().build())
                .client(okHttpClient)
                .build();
    }

}
