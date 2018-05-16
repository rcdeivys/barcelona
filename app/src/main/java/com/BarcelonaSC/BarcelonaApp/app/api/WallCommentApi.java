package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.WallCommentResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentClap;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentCreate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public interface WallCommentApi {

    @GET("comentarios_post/{idpost}")
    Call<WallCommentResponse> getWallComment(@Path("idpost") String idpost,
                                             @Query("token") String token,
                                             @Query("page") int page);

    @POST("muro_comentar")
    Call<GenericResponse> createComment(@Body() WallCommentCreate comment);

    @POST("muro_comentario_aplaudir")
    Call<GenericResponse> clapComment(@Body() WallCommentClap comment);

    @DELETE("muro_comentar/{idpost}/{idcomment}/{token}")
    Call<GenericResponse> deleteComment(@Path("idpost") String idpost,
                                        @Path("idcomment") String idcomment,
                                        @Path("token") String token);

    @POST("muro_edit_coment/{idpost}/{idcomment}/{token}")
    Call<GenericResponse> editComment(@Path("idpost") String idpost,
                                      @Path("idcomment") String idcomment,
                                      @Path("token") String token,
                                      @Body() WallCommentCreate comment);
}
