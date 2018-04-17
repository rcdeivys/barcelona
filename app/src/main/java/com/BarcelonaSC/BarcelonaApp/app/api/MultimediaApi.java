package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaOnLineResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Deivys on 3/29/2018.
 */

public interface MultimediaApi {

    @GET("video_noticia")
    Call<MultimediaVideoResponse> getVideosNoticias();

    @GET("multimedia")
    Call<MultimediaOnLineResponse> getVideosStreaming();

}
