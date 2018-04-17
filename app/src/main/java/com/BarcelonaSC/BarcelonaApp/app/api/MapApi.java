package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.MapResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public interface MapApi {

    @GET("punto_referencia")
    Call<MapResponse> getPoints();

}