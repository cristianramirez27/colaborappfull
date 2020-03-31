package com.coppel.rhconecta.dev.data.visionary;

import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionaries_previews.GetVisionariesPreviewsResponse;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdRequest;
import com.coppel.rhconecta.dev.data.visionary.model.get_visionary_by_id.GetVisionaryByIdResponse;

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

    /**
     *
     * @param authHeader
     * @param url
     * @param request
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetVisionaryByIdResponse> getVisionaryById(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetVisionaryByIdRequest request
    );

}
