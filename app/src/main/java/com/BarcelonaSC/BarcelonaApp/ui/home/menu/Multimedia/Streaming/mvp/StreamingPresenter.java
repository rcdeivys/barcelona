package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.mvp;


import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaStreamingData;

import java.util.List;

/**
 * Created by Deivys on 3/29/2018.
 */

public class StreamingPresenter implements StreamingContract.Presenter, StreamingContract.ModelResultListener {

    private static final String TAG = StreamingPresenter.class.getSimpleName();
    private StreamingContract.View view;
    private StreamingModel model;
    String type;

    private MultimediaStreamingData streamingData;

    public StreamingPresenter(StreamingContract.View view, StreamingModel StreamingModel, String type) {
        this.view = view;
        this.model = StreamingModel;
        this.type = type;
    }

    @Override
    public void onAttach(StreamingContract.View view) {
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

    }

    @Override
    public void onGetVideosFailed() {

    }

    @Override
    public void onGetStreamingSuccess(MultimediaStreamingData listStreaming) {
        if (isViewNull()) return;
        this.streamingData = listStreaming;
        view.setStreaming(listStreaming);
        view.hideProgress();
    }

    @Override
    public void onGetStreamingFailed() {
        if (isViewNull()) return;
        view.showToastError("Error al cargar los datos");
        view.hideProgress();
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
