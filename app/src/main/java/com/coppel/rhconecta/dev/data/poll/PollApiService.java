package com.coppel.rhconecta.dev.data.poll;

import com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count.GetAvailablePollCountRequest;
import com.coppel.rhconecta.dev.data.poll.model.get_available_poll_count.GetAvailablePollCountResponse;
import com.coppel.rhconecta.dev.data.poll.model.get_poll.GetPollRequest;
import com.coppel.rhconecta.dev.data.poll.model.get_poll.GetPollResponse;
import com.coppel.rhconecta.dev.data.poll.model.send_poll.SendPollRequest;
import com.coppel.rhconecta.dev.data.poll.model.send_poll.SendPollResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 *
 */
public interface PollApiService {

    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetPollResponse> getPoll(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body GetPollRequest request
    );

    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<SendPollResponse> sendPoll(
            @Header("Authorization") String authHeader,
            @Url String url,
            @Body SendPollRequest request
    );

    /**
     *
     */
    @Headers("Content-Type: application/json")
    @POST
    Call<GetAvailablePollCountResponse> getAvailablePollCount(
            @Header("Authorization") String authHeader,
            @Header("X-Coppel-Date-Request") String dateRequest,
            @Header("X-Coppel-Latitude") String latitude,
            @Header("X-Coppel-Longitude") String longitude,
            @Header("X-Coppel-TransactionId") String transactionId,
            @Url String url,
            @Body GetAvailablePollCountRequest request
    );

}
