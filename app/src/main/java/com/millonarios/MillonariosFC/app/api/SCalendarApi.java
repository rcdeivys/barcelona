package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.SCalendarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Amplex on 7/11/2017.
 */

public interface SCalendarApi {

    @GET("single_calendario/{id}")
    Call<SCalendarResponse> getNoticias(@Path("id") int id);

    @GET("single_calendariofb/{id}")
    Call<SCalendarResponse> getNoticiasFb(@Path("id") int id);

}