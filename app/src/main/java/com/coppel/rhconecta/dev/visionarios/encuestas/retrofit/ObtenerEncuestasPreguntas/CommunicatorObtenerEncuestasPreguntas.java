package com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas;

import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.encuestas.objects.Pregunta;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Events.ObtenerEncuestasPreguntasEvent;
import com.coppel.rhconecta.dev.visionarios.encuestas.retrofit.ObtenerEncuestasPreguntas.Request.JSON_ObtenerEncuestasPreguntas;
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

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class CommunicatorObtenerEncuestasPreguntas {
    private static final String TAG = "CommunicatorObtenerEncuestasPreguntas";
    private String SERVER_URL = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL);

    public void ObtenerApi(JSON_ObtenerEncuestasPreguntas item, final ObtenerEncuestasPreguntas_Callback callback) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);

        final String token = "Bearer";



        /*String varToken = "688900-93827865-775a3ac0496611e894b80242ac11000e";
        AddHeaderInterceptor authorization = new AddHeadAddHeaderInterceptorerInterceptor(varToken);
        httpClient.addNetworkInterceptor(authorization);*/
        /*
        InternalDatabase idb = new InternalDatabase(App.context);
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


        InterfaceObtenerEncuestasPreguntas service = retrofit.create(InterfaceObtenerEncuestasPreguntas.class);
        Call<ArrayList<Pregunta>> call = service.post(token, item);


        call.enqueue(new Callback<ArrayList<Pregunta>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Pregunta>> call, @NonNull Response<ArrayList<Pregunta>> response) {
                BusProvider.getInstance().post(new ObtenerEncuestasPreguntasEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Pregunta>> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseObtenerEncuestasPreguntas>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerEncuestasPreguntas> call, @NonNull Response<ResponseObtenerEncuestasPreguntas> response) {
                BusProvider.getInstance().post(new ObtenerEncuestasPreguntasEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerEncuestasPreguntas> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}