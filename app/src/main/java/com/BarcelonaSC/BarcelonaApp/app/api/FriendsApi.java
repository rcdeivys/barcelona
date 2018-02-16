package com.BarcelonaSC.BarcelonaApp.app.api;

/**
 * Created by Cesar on 17/01/2018.
 */

import com.BarcelonaSC.BarcelonaApp.models.response.FriendDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendsApi {

    @GET("amigos")
    Call<FriendDataResponse> getChatGroups(@Query("page") int page);

}
