package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.WallCommentResponse;
import com.millonarios.MillonariosFC.ui.wall.GenericResponse;
import com.millonarios.MillonariosFC.ui.wall.comment.WallCommentClap;
import com.millonarios.MillonariosFC.ui.wall.comment.WallCommentCreate;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
