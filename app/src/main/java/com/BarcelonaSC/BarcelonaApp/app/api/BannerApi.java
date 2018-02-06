package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.BannerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Carlos on 06/12/2017.
 */

public interface BannerApi {

    @GET("banners")
    Call<BannerResponse> getBanners();
}
