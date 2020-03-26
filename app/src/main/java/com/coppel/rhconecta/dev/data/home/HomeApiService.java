package com.coppel.rhconecta.dev.data.home;

import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationRequest;
import com.coppel.rhconecta.dev.data.home.model.get_main_information.GetMainInformationResponse;

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
public interface HomeApiService {

    /**
     *
     * @param authHeader
     * @param url
     * @param request
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetMainInformationResponse> getMainInformation(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetMainInformationRequest request
    );

}
