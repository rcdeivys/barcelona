package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.WallSearchResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WallSearchApi {

    @POST("SearchMuro")
    @FormUrlEncoded
    Call<WallSearchResponse> searchProfile(@Field("busqueda") String search,
                                           @Query("page") String page);
}
