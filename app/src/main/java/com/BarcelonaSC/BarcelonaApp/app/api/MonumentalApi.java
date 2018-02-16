package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.VotesMonumental;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalPollResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalRankingResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public interface MonumentalApi {

    @GET("monumentales_encuesta")
    Call<MonumentalPollResponse> getPollMonumental();

    @GET("ranking_monumentales")
    Call<MonumentalRankingResponse> getRanking();

    @GET("single_monumental/{id}")
    Call<MonumentalResponse> getMonumentals(@Path("id") String id);

    @POST("votar_monumental")
    Call<MonumentalResponse> getVote(@Body VotesMonumental monumental);


}
