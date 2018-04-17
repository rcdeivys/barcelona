package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaStreamingData;

import java.util.List;

/**
 * Created by Deivys on 3/29/2018.
 */

public class VideoContract {

    public interface ModelResultListener {

        void onGetVideosSuccess(List<MultimediaDataResponse> listVideos);

        void onGetVideosFailed();

        void onGetStreamingSuccess(MultimediaStreamingData listStreaming);

        void onGetStreamingFailed();
    }

    public interface Presenter extends MVPContract.Presenter<VideoContract.View> {

        void loadVideos();

        void loadStreaming();

        void onclickVideoItem(int position);

    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void setVideos(List<MultimediaDataResponse> listVideos);

        void setStreaming(MultimediaStreamingData streamingData);

    }

}
