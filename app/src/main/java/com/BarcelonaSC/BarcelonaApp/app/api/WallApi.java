package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.models.response.WallProfileResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.WallResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreateClapPost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @Multipart
    @POST("muro")
    Call<GenericResponse> createVideoPost(@Part("token") RequestBody token
            , @Part("mensaje") RequestBody mensaje
            , @Part("tipo_post") RequestBody tipo_post
            , @Part("foto") RequestBody foto
            , @Part("thumbnail") RequestBody thumbnail);

    @GET("post/{id}/usuario/{token}")
    Call<SinglePostResponse> getIDPost(@Path("token") String token,
                                       @Path("id") String id);

    @PUT("muro/{idpost}")
    Call<GenericResponse> editPost(
            @Path("idpost") String idPost,
            @Body WallCreatePost post);

    @POST("muro_reporte")
    Call<GenericResponse> reportarPost(
            @Body WallReportarPost post);

    @GET("perfil/{token}")
    Call<WallProfileResponse> profileWall(
            @Path("token") String token,
            @Query("page") int page);
}
