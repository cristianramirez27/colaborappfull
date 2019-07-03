package com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.comunicados.Retrofit.ObtenerComunicados.Events.ComunicadoVistoEvent;
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

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ComunicadoVisto {
    private static final String TAG = "CommunicatorObtenerComunicados";
    private String SERVER_URL = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL);


    public void ObtenerApi(JSON_ObtenerComunicados item, final ComunicadoVisto_Callback callback) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);

        final String token = "Bearer";


        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();


        InterfaceComunicadoVisto service = retrofit.create(InterfaceComunicadoVisto.class);
        Call<ArrayList<Comunicado>> call = service.post(token, item);


        call.enqueue(new Callback<ArrayList<Comunicado>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Comunicado>> call, @NonNull Response<ArrayList<Comunicado>> response) {
                BusProvider.getInstance().post(new ComunicadoVistoEvent(response.body(), callback));
            }

            @Override
            public void onFailure(Call<ArrayList<Comunicado>> call, Throwable t) {
                String message = "Error";

                if (t.getMessage() != null)
                    message = t.getMessage();

                Log.d("ComunicadoVisto",message);
            }

           /* @Override
            public void onFailure(@NonNull Call<ArrayList<Comunicado>> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }*/
        });
    }
}
