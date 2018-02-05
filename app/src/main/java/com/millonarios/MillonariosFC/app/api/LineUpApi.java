package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.GameSummonedResponse;
import com.millonarios.MillonariosFC.models.response.MyIdealElevenResponse;
import com.millonarios.MillonariosFC.models.response.PlaybyplayResponse;
import com.millonarios.MillonariosFC.models.response.SendMyIdealElevenData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Carlos on 13/11/2017.
 */

public interface LineUpApi {

    @GET("convocados")
    Call<GameSummonedResponse> getGameSummoned();

    @GET("playbyplay")
    Call<PlaybyplayResponse> getPlayByPlay();

    @GET("onceideal/{token}")
    Call<MyIdealElevenResponse> getOnceIdeal(@Path("token") String token);

    @POST("onceideal")
    Call<JsonObject> setOnceIdeal(@Body SendMyIdealElevenData sendMyIdealElevenData);

}
