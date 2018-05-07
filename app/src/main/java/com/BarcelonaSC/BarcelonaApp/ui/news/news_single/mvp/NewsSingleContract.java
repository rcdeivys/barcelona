package com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.NewsSingleData;

import java.util.List;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

public class NewsSingleContract {

    public interface Presenter extends MVPContract.Presenter<View> {
        void getNewsSingle(String id);

        boolean isViewNull();
    }

    public interface ModelResultListener {
        void onGetSuccess(List<NewsSingleData> noticias);

        void onError(String error);
    }

    public interface View {
        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setNewsSingle(List<NewsSingleData> noticia);
    }

}