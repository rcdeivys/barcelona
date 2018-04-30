package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.SendReasonCancelation;
import com.BarcelonaSC.BarcelonaApp.models.response.SuscriptionStatusResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.TiposSuscripcionResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by makhnnar on 30/04/18.
 */

public interface HinchaDoradoApi {

    @GET("suscripciones/razonescancelarsuscripcion")
    Call<ReasonResponse> getReason();

    @POST("suscripciones/cancelar")
    Call<JsonObject> cancelSubscription(@Body SendReasonCancelation sendReasonCancelation);

    @GET("suscripciones/tipos")
    Call<TiposSuscripcionResponse> getTiposSuscripcion();

    @GET("suscripciones/usuario/{token}")
    Call<SuscriptionStatusResponse> getStatusSuscripcion(@Path("token") String token);


}
