package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.WallResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreateClapPost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public interface WallApi {

    @GET("muro")
    Call<WallResponse> getWall(@Query("token") String token,
                               @Query("page") int page);

    @POST("muro_aplaudir")
    Call<GenericResponse> createClapPost(@Body WallCreateClapPost clap);

    @DELETE("muro/{idpost}/{token}")
    Call<GenericResponse> detelePost(@Path("idpost") String idpost,
                                     @Path("token") String token);

    @POST("muro")
    Call<GenericResponse> createPost(@Body WallCreatePost post);

}
