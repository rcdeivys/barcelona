package com.BarcelonaSC.BarcelonaApp.app.api;

import com.BarcelonaSC.BarcelonaApp.models.response.GroupsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pedro Gomez on 17/01/2018.
 */

public interface GroupsApi {

    @GET("grupos")
    Call<GroupsResponse> getChatGroups(@Query("page") int page);

}
