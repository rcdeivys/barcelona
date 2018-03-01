package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.UserPhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Carlos on 05/02/2018.
 */
public interface UserPhotoApi {

    @GET("usuarios/image/by/id/{id}")
    Call<UserPhotoResponse> getPhoto(@Path("id") Long id);

}