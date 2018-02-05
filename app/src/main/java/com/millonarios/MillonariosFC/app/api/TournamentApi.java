package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.TournamentResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Amplex on 1/11/2017.
 */

public interface TournamentApi {

    @GET("calendario")
    Call<TournamentResponse> getTournaments();

    @GET("calendariofb")
    Call<TournamentResponse> getCalendariofb();

    @GET("partidos")
    Call<TournamentResponse> getMatches();

    @GET("copas")
    Call<TournamentResponse> get();

}