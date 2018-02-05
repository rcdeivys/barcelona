package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.BannerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Carlos on 06/12/2017.
 */

public interface BannerApi {

    @GET("banners")
    Call<BannerResponse> getBanners();
}
