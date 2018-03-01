package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.News;

import java.util.List;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalNewsContract {

    public interface ModelResultListener {
        void onGetMNewsSuccess(List<News> news);

        void onGetMNewsFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void loadNews();

        void loadNewsPage(int page);

        void onClickNewsItem(News news);
    }

    public interface View {

        void setMonumentalNews(List<News> news);

        void navigateToVideoNewsActivity(News news);

        void navigateToInfografiaActivity(News news);

        void navigateToNewsDetailsActivity(News news);

        void navigateToGalleryActivity(News news);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError();

    }

}