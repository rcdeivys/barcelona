package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.SetTokenToFirebaseIdRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by RYA-Laptop on 24/04/2018.
 */

public interface HomeApi {

    @POST("/api/usuarios/token")
    Call<JsonObject> setTokenToFirebaseId(@Body SetTokenToFirebaseIdRequest setTokenToFirebaseIdRequest);

}