package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.databases.InternalDatabase;
import com.coppel.rhconecta.dev.visionarios.databases.TableConfig;
import com.coppel.rhconecta.dev.visionarios.utils.App;
import com.coppel.rhconecta.dev.visionarios.utils.Config;
import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events.ObtenerVideosEvent;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Response.ResponseObtenerVideos;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class CommunicatorObtenerVideos {
    private static final String TAG = "CommunicatorObtenerVideos";
    private String SERVER_URL = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL);

    public void ObtenerApi(JSON_ObtenerVideos item, final ObtenerVideos_Callback callback) {


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


        InterfaceObtenerVideos service = retrofit.create(InterfaceObtenerVideos.class);
        Call<ResponseObtenerVideos> call = service.post(token, item);


        call.enqueue(new Callback<ResponseObtenerVideos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerVideos> call, @NonNull Response<ResponseObtenerVideos> response) {
                BusProvider.getInstance().post(new ObtenerVideosEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerVideos> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseObtenerVideos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerVideos> call, @NonNull Response<ResponseObtenerVideos> response) {
                BusProvider.getInstance().post(new ObtenerVideosEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerVideos> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}
