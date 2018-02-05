package com.millonarios.MillonariosFC.app.api;

/**
 * Created by Cesar on 17/01/2018.
 */

import com.millonarios.MillonariosFC.models.response.FriendDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendsApi {

    @GET("amigos")
    Call<FriendDataResponse> getChatGroups(@Query("page") int page);

}
