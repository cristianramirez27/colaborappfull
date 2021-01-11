package com.coppel.rhconecta.dev.data.release;

import com.coppel.rhconecta.dev.data.release.model.get_release_by_id.GetReleaseByIdRequest;
import com.coppel.rhconecta.dev.data.release.model.get_release_by_id.GetReleaseByIdResponse;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsRequest;
import com.coppel.rhconecta.dev.data.release.model.get_releases_previews.GetReleasesPreviewsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/* */
public interface ReleaseApiService {

    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetReleasesPreviewsResponse> getReleasesPreviews(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetReleasesPreviewsRequest request
    );

    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetReleaseByIdResponse> getReleaseById(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetReleaseByIdRequest request
    );

}
