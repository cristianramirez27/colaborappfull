package com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle;

import android.support.annotation.NonNull;

import com.coppel.rhconecta.dev.visionarios.utils.ConstantesGlobales;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Events.ErrorEvent;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Events.ObtenerVideosDetalleEvent;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Request.JSON_ObtenerVideosDetalle;
import com.coppel.rhconecta.dev.visionarios.videos.retrofit.ObtenerVideosDetalle.Response.ResponseObtenerVideosDetalle;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicatorObtenerVideosDetalle {
    private static final String TAG = "CommunicatorObtenerVideosDetalle";
    private static final String SERVER_URL = ConstantesGlobales.URL_API;


    public void ObtenerApi(JSON_ObtenerVideosDetalle item, final ObtenerVideosDetalle_Callback callback) {


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


        InterfaceObtenerVideosDetalle service = retrofit.create(InterfaceObtenerVideosDetalle.class);
        Call<ResponseObtenerVideosDetalle> call = service.post(token, item);


        call.enqueue(new Callback<ResponseObtenerVideosDetalle>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerVideosDetalle> call, @NonNull Response<ResponseObtenerVideosDetalle> response) {
                BusProvider.getInstance().post(new ObtenerVideosDetalleEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerVideosDetalle> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message, callback));
            }
        });


     /*   call.enqueue(new Callback<ResponseObtenerVideosDetalle>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObtenerVideosDetalle> call, @NonNull Response<ResponseObtenerVideosDetalle> response) {
                BusProvider.getInstance().post(new ObtenerVideosDetalleEvent(response.body(), callback));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObtenerVideosDetalle> call, @NonNull Throwable t) {
                String message = "Error";
                if (t.getMessage() != null)
                    message = t.getMessage();
                BusProvider.getInstance().post(new ErrorEvent(-2, message,callback));
            }
        });*/
    }
}
