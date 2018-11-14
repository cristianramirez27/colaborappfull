package com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin;

import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Events.GuardarLoginEvent;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Request.JSON_GuardarLogin;
import com.coppel.rhconecta.dev.visionarios.inicio.Retrofit.GuardarLogin.Response.ResponseGuardarLogin;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicatorGuardarLogin {
    private static final String TAG = "CommunicatorGuardarLogin";
    private static final String SERVER_URL = ConstantesGlobales.URL_API;


    public void ObtenerApi(JSON_GuardarLogin item, final GuardarLogin_Callback callback) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);

        final String token = "Bearer";



        /*String varToken = "688900-93827865-775a3ac0496611e894b80242ac11000e";
        AddHeaderInterceptor authorization = new AddHeadAddHeaderInterceptorerInterceptor(varToken);
        httpClient.addNetworkInterceptor(authorization);*/

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();


        InterfaceGuardarLogin service = retrofit.create(InterfaceGuardarLogin.class);
        Call<ResponseGuardarLogin> call = service.post(token, item);


        call.enqueue(new Callback<ResponseGuardarLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarLogin> call, @NonNull Response<ResponseGuardarLogin> response) {
                BusProvider.getInstance().post(new GuardarLoginEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarLogin> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseGuardarLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarLogin> call, @NonNull Response<ResponseGuardarLogin> response) {
                BusProvider.getInstance().post(new GuardarLoginEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarLogin> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}
