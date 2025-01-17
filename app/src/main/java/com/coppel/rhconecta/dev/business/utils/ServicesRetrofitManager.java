package com.coppel.rhconecta.dev.business.utils;

import com.coppel.rhconecta.dev.BuildConfig;
import com.coppel.rhconecta.dev.CoppelApp;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.security.cert.CertificateException;
import java.util.Objects;
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

import static com.coppel.rhconecta.dev.business.Configuration.AppConfig.URL_MAIN;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.TIME_OUT;
import static com.coppel.rhconecta.dev.business.utils.ServicesConstants.URL_BASE;

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
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            //OkHttpClient.Builder builder = new OkHttpClient.Builder();
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
    public Retrofit getRetrofitAPI() {
        String URL_BASE = AppUtilities.getStringFromSharedPreferences(CoppelApp.getContext(), URL_MAIN);
        String urlBase = (URL_BASE == null || URL_BASE.isEmpty()) ? BuildConfig.URL : URL_BASE;

        return new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(getUnsafeOkHttpClient().build())
                .client(okHttpClient)
                .build();
    }

    public Retrofit getRetrofitAPILocal() {
        /*OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CommonHeadersInterceptor(dateRequest, token, latitude, longitude, transactionId))
                .build();*/
        return new Retrofit.Builder()
                //.baseUrl("http://192.168.143.61:8080/v1/cartaslaborales/")
                //.baseUrl(" http://192.168.131.136:8080/v1/videos/")
                .baseUrl("http://192.168.134.250:8080/v1/beneficios/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(getUnsafeOkHttpClient().build())
                .client(okHttpClient)
                .build();
    }

}
