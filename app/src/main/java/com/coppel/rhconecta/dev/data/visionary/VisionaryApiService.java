package com.coppel.rhconecta.dev.data.visionary;

import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsResponse;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdResponse;
import com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id.UpdateVisionaryStatusByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.update_visionary_status_by_id.UpdateVisionaryStatusByIdResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/** */
public interface VisionaryApiService {

    @Headers("Content-Type: application/json")
    @POST
    Call<GetVisionariesPreviewsResponse> getVisionariesPreviews(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body GetVisionariesPreviewsRequest request
    );


    @Headers("Content-Type: application/json")
    @POST
    Call<GetVisionaryByIdResponse> getVisionaryById(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body GetVisionaryByIdRequest request
    );


    @Headers("Content-Type: application/json")
    @POST
    Call<UpdateVisionaryStatusByIdResponse> updateVisionaryStatusById(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body UpdateVisionaryStatusByIdRequest request
    );

}
