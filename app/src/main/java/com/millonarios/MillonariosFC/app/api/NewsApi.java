package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public interface NewsApi {


    @GET("noticias/{token}")
    Call<NewsResponse> getNewsProfessional(@Path("token") String token,
                                           @Query("page") int page);

    @GET("noticias_futbolbase")
    Call<NewsResponse> getNewsFootballBase(@Query("page") int page);

    @GET("noticias_monumentales")
    Call<NewsResponse> getMonumentalNews(@Query("page") int page);

}
