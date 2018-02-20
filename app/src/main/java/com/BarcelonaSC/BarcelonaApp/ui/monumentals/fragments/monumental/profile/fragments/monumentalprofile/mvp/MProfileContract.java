package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.BarcelonaSC.BarcelonaApp.models.News;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MProfileContract {

    public interface ModelResultListener {
        void onGetMonumentalSuccess(MonumentalItem itemList);

        void onGetMonumentalFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void loadMonumental(String id);

        void voteMonumental(String idMonumental, String idEncuesta, String emei);

        void clickItem(News news);

        void cancel();

        void reset();
    }

    public interface View {
        void showMonumental(MonumentalItem itemList);

        void updateVotes();

        void onFailed(String msg);

        void navigateToVideoNewsActivity(News news);

        void navigateToInfografiaActivity(News news);

        void navigateToNewsDetailsActivity(News news);

        void navigateToGalleryActivity(int id);
    }

}