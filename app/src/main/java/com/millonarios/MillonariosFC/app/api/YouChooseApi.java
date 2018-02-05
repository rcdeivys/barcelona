package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.SendChooseVote;
import com.millonarios.MillonariosFC.models.response.ChooseProfileResponse;
import com.millonarios.MillonariosFC.models.response.ChooseEncuestaResponse;
import com.millonarios.MillonariosFC.models.response.ChooseRankingResponse;
import com.millonarios.MillonariosFC.models.response.SendChooseVoteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Carlos on 09/01/2018.
 */

public interface YouChooseApi {

    @GET("encuesta/{token}")
    Call<ChooseEncuestaResponse> getEncuestas(@Path("token") String token);

    @GET("single_respuesta/{idrespuesta}")
    Call<ChooseProfileResponse> getChooseProfile(@Path("idrespuesta") String idRespuesta);

    @GET("ranking_encuestas/{idrespuesta}")
    Call<ChooseRankingResponse> getChooseRanking(@Path("idrespuesta") int idRespuesta);

    @POST("encuesta_votar")
    Call<SendChooseVoteResponse> sendVote(@Body SendChooseVote sendChooseVote);
}
