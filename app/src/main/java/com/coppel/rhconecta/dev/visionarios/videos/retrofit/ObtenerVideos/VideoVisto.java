package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coppel.rhconecta.dev.business.Configuration.AppConfig;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;
import com.coppel.rhconecta.dev.visionarios.videos.objects.Video;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Events.VideoVistoEvent;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideos.Request.JSON_ObtenerVideos;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class VideoVisto {
    private static final String TAG = "CommunicatorObtenerVideos";
    private String SERVER_URL = AppUtilities.getStringFromSharedPreferences(getApplicationContext(), AppConfig.VISIONARIOS_URL);


    public void ObtenerApi(JSON_ObtenerVideos item, final VideoVisto_Callback callback) {


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


        InterfaceVideoVisto service = retrofit.create(InterfaceVideoVisto.class);
        Call<ArrayList<Video>> call = service.post(token, item);


        call.enqueue(new Callback<ArrayList<Video>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Video>> call, @NonNull Response<ArrayList<Video>> response) {
                BusProvider.getInstance().post(new VideoVistoEvent(response.body(), callback));
            }

            @Override
            public void onFailure(Call<ArrayList<Video>> call, Throwable t) {
                String message = "Error";

                if (t.getMessage() != null)
                    message = t.getMessage();

                Log.d("VideoVisto",message);
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
