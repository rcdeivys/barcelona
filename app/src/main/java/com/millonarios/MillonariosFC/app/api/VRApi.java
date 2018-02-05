package com.millonarios.MillonariosFC.app.api;

import com.millonarios.MillonariosFC.models.response.VirtualRealityResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Carlos on 08/11/2017.
 */

public interface VRApi {

    @GET("videos360")
    Call<VirtualRealityResponse> getVideos();
}
