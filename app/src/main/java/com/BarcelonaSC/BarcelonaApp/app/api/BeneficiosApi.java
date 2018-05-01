package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.BeneficiosResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by makhnnar on 30/04/18.
 */

public interface BeneficiosApi {

    @GET("suscripciones/beneficios")
    Call<BeneficiosResponse> getBeneficios();

}