package com.millonarios.MillonariosFC.ui.virtualreality.mvp;


import com.millonarios.MillonariosFC.app.api.VRApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.VirtualRealityResponse;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class VRModel {

    private static final String TAG = VRModel.class.getSimpleName();
    private VRApi vrApi;

    public VRModel(VRApi vrApi) {
        this.vrApi = vrApi;
    }

    public void loadVideosProffessional(final VRContract.ModelResultListener result) {

        vrApi.getVideos().enqueue(new NetworkCallBack<VirtualRealityResponse>() {
            @Override
            public void onRequestSuccess(VirtualRealityResponse response) {
                if (response.getData() != null)
                    result.onGetVideosSuccess(response.getData());
                else
                    result.onGetVideosFailed("");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetVideosFailed("");
            }
        });


    }


}
