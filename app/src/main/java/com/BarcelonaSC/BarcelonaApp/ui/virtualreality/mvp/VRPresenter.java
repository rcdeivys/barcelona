package com.BarcelonaSC.BarcelonaApp.ui.virtualreality.mvp;


import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.VideoReality;

import java.util.ArrayList;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class VRPresenter implements VRContract.Presenter, VRContract.ModelResultListener {

    private static final String TAG = VRPresenter.class.getSimpleName();
    private VRContract.View view;
    private VRModel vrModel;

    private ArrayList<VideoReality> videoRealities;

    public VRPresenter(VRContract.View view, VRModel vrModel) {
        this.view = view;
        this.vrModel = vrModel;
        videoRealities = new ArrayList<>();
    }


    @Override
    public void onAttach(VRContract.View view) {
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
    public void onGetVideosSuccess(ArrayList<VideoReality> videoRealities) {
        if (isViewNull()) return;

        this.videoRealities = videoRealities;
        view.setVideos(videoRealities);
    }

    @Override
    public void onGetVideosFailed(String string) {
        if (isViewNull()) return;
        view.showToast(string);
    }

    @Override
    public void loadVideos() {
        vrModel.loadVideosProffessional(this);
    }

    @Override
    public void onclickVideoItem(int position) {
        if (isViewNull()) return;
        //Log.i("ITEMVR"," ---> "+videoRealities.get(position).toString());
        view.navigateVirtualActivity(videoRealities.get(position));
    }


}
