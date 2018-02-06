package com.BarcelonaSC.BarcelonaApp.ui.gallery.mvp;


import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.GalleryItem;

import java.util.List;

/**
 * Created by Leonardojpr on 10/16/17.
 */

public class GalleryContract {

    public interface ModelResultListener {

        void onSuccess(List<GalleryItem> news);

        void onGetGalleryFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void loadGallery(int id);
        void onclickGalleryItem(int position);
    }

    public interface View {
        void setGallery(List<GalleryItem> news);
        void navigateToGalleryActivity(List<GalleryItem> news, int position);
        void showProgress();
        void hideProgress();
        void setRefreshing(boolean state);
        void showToastError();

    }

}
