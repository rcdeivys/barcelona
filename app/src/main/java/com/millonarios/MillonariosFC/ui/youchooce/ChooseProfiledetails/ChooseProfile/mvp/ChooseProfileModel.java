package com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp;

import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.app.api.YouChooseApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.PlayerApplause;
import com.millonarios.MillonariosFC.models.response.ApplauseResponse;
import com.millonarios.MillonariosFC.models.response.ChooseProfileResponse;
import com.millonarios.MillonariosFC.models.response.PlayerResponse;
import com.millonarios.MillonariosFC.models.response.SetApplauseResponse;

import java.util.List;

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
