package com.millonarios.MillonariosFC.app.api;


import com.millonarios.MillonariosFC.models.PlayerApplause;
import com.millonarios.MillonariosFC.models.response.ApplauseResponse;
import com.millonarios.MillonariosFC.models.response.GameSummonedResponse;
import com.millonarios.MillonariosFC.models.response.MostApplaudedPlayerResponse;
import com.millonarios.MillonariosFC.models.response.PlayerResponse;
import com.millonarios.MillonariosFC.models.response.PlayoffResponse;
import com.millonarios.MillonariosFC.models.response.SetApplauseResponse;

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

    @GET("single_jugador/{id}")
    Call<PlayerResponse> getPlayerData(@Path("id") String idjugador);

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
