package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileApi {

    @PUT("usuarios/{token}")
    Call<GenericResponse> update(
            @Path("token") String token,
            @Body User user);

    @GET("usuarios/{token}")
    Call<UserResponse> get(@Path("token") String token);

}