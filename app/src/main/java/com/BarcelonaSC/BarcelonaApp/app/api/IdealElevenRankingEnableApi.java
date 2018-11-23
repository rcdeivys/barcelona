package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.IdealElevenRankingEnableResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IdealElevenRankingEnableApi {

    @GET("onceideal_ranking_enabled")
    Call<IdealElevenRankingEnableResponse> getEnable();
}
