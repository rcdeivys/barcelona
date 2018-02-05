package com.millonarios.MillonariosFC.ui.gallery.mvp;


import com.millonarios.MillonariosFC.app.api.GalleryApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.GalleryResponse;

/**
 * Created by Leonardojpr on 10/16/17.
 */

public class GalleryModel {

    private static final String TAG = GalleryModel.class.getSimpleName();
    private GalleryApi galleryApi;

    public GalleryModel(GalleryApi galleryApi) {
        this.galleryApi = galleryApi;
    }

    public void loadGallery(int id, final GalleryContract.ModelResultListener result) {

        galleryApi.getGallery(id).enqueue(new NetworkCallBack<GalleryResponse>() {
            @Override
            public void onRequestSuccess(GalleryResponse response) {
                result.onSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetGalleryFailed();
            }
        });

    }
}
