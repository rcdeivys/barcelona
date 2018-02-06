package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.ReferredToResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

public interface ReferredApi {

    @GET("consultar_referidos/{token}")
    Call<ReferredToResponse> getReferidos(@Path("token") String token,
                                          @Query("p") String page);

}