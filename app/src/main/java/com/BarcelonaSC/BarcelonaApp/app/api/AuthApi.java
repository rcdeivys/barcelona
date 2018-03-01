package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.ValidateAccItem;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Amplex on 12/10/2017.
 */

public interface AuthApi {

    @POST("auth2")
    Call<AuthResponse> signIn(@Body User user);

    @POST("auth_redes")
    Call<AuthResponse> signInSocial(@Body User user);

    @POST("usuarios2")
    Call<GenericResponse> signUp(@Body User user);

    @POST("validar_cuenta")
    Call<AuthResponse> validateAccount(@Body ValidateAccItem validateAccItem);

    @GET("reenviar_pin_confirmacion/{email}")
    Call<GenericResponse> resendCode(@Path("email") String email);

}
