package com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.WallSearchApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.WallSearchResponse;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class WallSearchModel {

    private static final String TAG = WallSearchModel.class.getSimpleName();
    List<FriendsModelView> listfriends;

    private WallSearchApi wallSearchApi;

    public WallSearchModel(WallSearchApi wallSearchApi) {
        this.wallSearchApi = wallSearchApi;
    }

    public void search(String search, final String page, final WallSearchContract.ModelResultListener resultListener) {
        wallSearchApi.searchProfile(search, page).enqueue(new NetworkCallBack<WallSearchResponse>() {
            @Override
            public void onRequestSuccess(WallSearchResponse response) {
                resultListener.onGetSearchSuccess(response.getData(), Integer.parseInt(page) == 1 ? false : true);
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onSearchFailed();
            }
        });
    }
}
