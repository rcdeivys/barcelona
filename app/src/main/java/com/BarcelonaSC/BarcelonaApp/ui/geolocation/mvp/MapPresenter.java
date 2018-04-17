package com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp;

import com.BarcelonaSC.BarcelonaApp.models.MapData;

import java.util.List;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public class MapPresenter implements MapContract.Presenter, MapContract.ModelResultListener {

    private MapContract.View view;
    private MapModel model;
    private List<MapData> points;

    public MapPresenter(MapContract.View view, MapModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(MapContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getPoints() {
        model.getPoints(this);
    }

    @Override
    public void onGetPointsSuccess(List<MapData> points) {
        this.points = points;
        if (isViewNull()) return;
        view.setPoints(this.points);
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

}