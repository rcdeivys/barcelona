package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MultimediaApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaOnLineResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaVideoResponse;

/**
 * Created by Deivys on 3/29/2018.
 */

public class VideoModel {

    private static final String TAG = VideoModel.class.getSimpleName();
    private MultimediaApi multimediaApi;

    public VideoModel(MultimediaApi multimediaApi) {
        this.multimediaApi = multimediaApi;
    }

    public void loadVideos(final VideoContract.ModelResultListener result) {
        multimediaApi.getVideosNoticias().enqueue(new NetworkCallBack<MultimediaVideoResponse>() {
            @Override
            public void onRequestSuccess(MultimediaVideoResponse response) {
                if (response.getData() != null)
                    result.onGetVideosSuccess(response.getData());
                else
                    result.onGetVideosFailed();
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetVideosFailed();
            }
        });
    }

    public void loadStreaming(final VideoContract.ModelResultListener result) {

        multimediaApi.getVideosStreaming().enqueue(new NetworkCallBack<MultimediaOnLineResponse>() {
            @Override
            public void onRequestSuccess(MultimediaOnLineResponse response) {
                if (response.getData() != null)
                    result.onGetStreamingSuccess(response.getData());
                else
                    result.onGetStreamingFailed();
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetStreamingFailed();
            }
        });
    }

}
