package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados;

import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events.ObtenerComunicadosEvent;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Request.JSON_ObtenerComunicados;
import com.coppel.rhconecta.dev.visionarios.comunicados.objects.Comunicado;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicatorObtenerComunicados {

    private static final String TAG = "CommunicatorObtenerComunicados";
    private static final String SERVER_URL = ConstantesGlobales.URL_API;


    public void ObtenerApi(JSON_ObtenerComunicados item, final ObtenerComunicados_Callback callback) {


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


        InterfaceObtenerComunicados service = retrofit.create(InterfaceObtenerComunicados.class);
        Call<ArrayList<Comunicado>> call = service.post(token, item);


        call.enqueue(new Callback<ArrayList<Comunicado>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Comunicado>> call, @NonNull Response<ArrayList<Comunicado>> response) {
                BusProvider.getInstance().post(new ObtenerComunicadosEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Comunicado>> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseObtenerComunicados>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerComunicados> call, @NonNull Response<ResponseObtenerComunicados> response) {
                BusProvider.getInstance().post(new ObtenerComunicadosEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerComunicados> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}

