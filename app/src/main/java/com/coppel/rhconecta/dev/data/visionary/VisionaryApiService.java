package com.coppel.rhconecta.dev.data.visionary;

import com.coppel.rhconecta.dev.data.visionary.model.GetVisionariesPreviewsRequest;
import com.coppel.rhconecta.dev.data.visionary.model.GetVisionariesPreviewsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 *
 *
 */
public interface VisionaryApiService {

    /**
     *
     * @param authHeader
     * @param url
     * @param request
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetVisionariesPreviewsResponse> getVisionariesPreviews(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetVisionariesPreviewsRequest request
    );

}
