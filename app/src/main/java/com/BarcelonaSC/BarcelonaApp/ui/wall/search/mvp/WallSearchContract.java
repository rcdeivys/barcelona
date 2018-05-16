package com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class WallSearchContract {

    public interface ModelResultListener {

        void onGetSearchSuccess(List<WallSearchItem> searchItems, boolean page);

        void onSearchFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void search(String name);

        void searchPage(String name, String page);

    }

    public interface View {

        void search(List<WallSearchItem> friends);

        void searchPage(List<WallSearchItem> friends);

        void showProgress();

        void hideProgress();

        void showToastError(String errror);

        void refresh();
    }
}
