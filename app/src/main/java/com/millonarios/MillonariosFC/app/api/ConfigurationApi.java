package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.ConfigurationResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public interface ConfigurationApi {

    @GET("configuracion")
    Call<ConfigurationResponse> getConfiguration();
}
