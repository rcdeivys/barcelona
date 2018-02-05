package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.response.GenericResponse;
import com.millonarios.MillonariosFC.models.response.UserResponse;

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