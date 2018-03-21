package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.YouChooseApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.ChooseProfileResponse;

/**
 * Created by Carlos on 13/10/2017.
 */

public class ChooseProfileModel {

    private static final String TAG = ChooseProfileModel.class.getSimpleName();
    private YouChooseApi youChooseApi;

    public ChooseProfileModel(YouChooseApi youChooseApi) {
        this.youChooseApi = youChooseApi;
    }

    public void getChooseProfile(String profileId,final ChooseProfileContract.ModelResultListener listener) {
        youChooseApi.getChooseProfile(profileId).enqueue(new NetworkCallBack<ChooseProfileResponse>() {
            @Override
            public void onRequestSuccess(ChooseProfileResponse response) {
                if (response.getData() != null) {
                    listener.onGetChooseProfileSuccess(response.getData());
                }else{
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }


}
