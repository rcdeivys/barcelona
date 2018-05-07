package com.BarcelonaSC.BarcelonaApp.app.api;


import com.BarcelonaSC.BarcelonaApp.models.PlayerApplause;
import com.BarcelonaSC.BarcelonaApp.models.response.ApplauseResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.GameSummonedResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MostApplaudedPlayerResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.PlayerResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.PlayoffResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.SetApplauseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Carlos on 11/10/2017.
 */

public interface TeamApi {

    @GET("nomina")
    Call<PlayoffResponse> getPlayoffSummoned();

    @GET("nominafb")
    Call<PlayoffResponse> getPlayoffFB();

    @GET("single_jugador/{id}/{token}")
    Call<PlayerResponse> getPlayerData(@Path("id") String idjugador, @Path("token") String token);

    @GET("single_jugadorfb/{id}")
    Call<PlayerResponse> getPlayerDataFB(@Path("id") String idjugador);

    @POST("aplaudir")
    Call<SetApplauseResponse> setApplause(@Body PlayerApplause playerApplause);

    @GET("convocados")
    Call<GameSummonedResponse> getGameSummoned();

    @GET("aplausos")
    Call<ApplauseResponse> getApplause(@Query("idjugador") String idjugador);

    @GET("aplausos_equipo")
    Call<MostApplaudedPlayerResponse> getMosApplaudedPlayer();
}
