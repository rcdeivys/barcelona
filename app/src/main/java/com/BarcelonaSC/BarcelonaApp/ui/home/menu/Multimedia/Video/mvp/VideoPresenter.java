package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp;

import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaStreamingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deivys on 3/29/2018.
 */

public class VideoPresenter implements VideoContract.Presenter, VideoContract.ModelResultListener {

    private static final String TAG = VideoPresenter.class.getSimpleName();
    private VideoContract.View view;
    private VideoModel model;
    String type;

    private List<MultimediaDataResponse> listVideos;
    private MultimediaStreamingData streamingData;

    public VideoPresenter(VideoContract.View view, VideoModel VideoModel, String type) {
        this.view = view;
        this.model = VideoModel;
        this.type = type;
        listVideos = new ArrayList<>();
    }

    @Override
    public void onAttach(VideoContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    private boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onGetVideosSuccess(List<MultimediaDataResponse> listVideos) {
        if (isViewNull()) return;

        this.listVideos = listVideos;
        view.setVideos(this.listVideos);
        view.hideProgress();
    }

    @Override
    public void onGetVideosFailed() {
        if (isViewNull()) return;
        view.showToastError("Error al cargar los datos");
        view.hideProgress();
    }

    @Override
    public void onGetStreamingSuccess(MultimediaStreamingData listStreaming) {
        if (isViewNull()) return;

        this.streamingData = listStreaming;
        view.setStreaming(this.streamingData);
        view.hideProgress();
    }

    @Override
    public void onGetStreamingFailed() {

    }

    @Override
    public void loadVideos() {
        model.loadVideos(this);
    }

    @Override
    public void loadStreaming() {
        model.loadStreaming(this);
    }

    @Override
    public void onclickVideoItem(int position) {

    }
}
