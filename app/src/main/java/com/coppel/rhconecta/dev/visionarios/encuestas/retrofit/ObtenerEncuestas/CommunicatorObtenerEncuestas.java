package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas;

import android.content.Context;
import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Encuesta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Events.ObtenerEncuestasEvent;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestas.Request.JSON_ObtenerEncuestas;
import com.coppel.rhconecta.dev.visionarios.utils.App;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicatorObtenerEncuestas {
    private static final String TAG = "CommunicatorObtenerEncuestas";
    private String SERVER_URL = ConstantesGlobales.URL_API;

    public void ObtenerApi(JSON_ObtenerEncuestas item, final ObtenerEncuestas_Callback callback) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);

        final String token = "Bearer";



        /*String varToken = "688900-93827865-775a3ac0496611e894b80242ac11000e";
        AddHeaderInterceptor authorization = new AddHeadAddHeaderInterceptorerInterceptor(varToken);
        httpClient.addNetworkInterceptor(authorization);*/

        /*InternalDatabase idb = new InternalDatabase(App.context);
        TableConfig tableConfig = new TableConfig(idb,false);
        Config config = tableConfig.select("1");
        if(config !=null){
            SERVER_URL = config.getURL_VISIONARIOS();
        }*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();


        InterfaceObtenerEncuestas service = retrofit.create(InterfaceObtenerEncuestas.class);
        Call<ArrayList<Encuesta>> call = service.post(token, item);


        call.enqueue(new Callback<ArrayList<Encuesta>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Encuesta>> call, @NonNull Response<ArrayList<Encuesta>> response) {
                BusProvider.getInstance().post(new ObtenerEncuestasEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Encuesta>> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseObtenerEncuestas>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerEncuestas> call, @NonNull Response<ResponseObtenerEncuestas> response) {
                BusProvider.getInstance().post(new ObtenerEncuestasEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerEncuestas> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}
