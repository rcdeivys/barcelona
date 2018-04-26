package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.ChatReportData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by quint on 4/4/2018.
 */

public interface ChatApi {

    @POST("ChatReporte")
    Call<JsonObject> reportarChat(
            @Body ChatReportData chat);
}
