package com.BarcelonaSC.BarcelonaApp.ui.news.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.News;

import java.util.List;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class NewsContract {

    public interface ModelResultListener {

        void onGetNewsSuccess(List<Object> news, String category);

        void onGetNewsFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void loadNews(String category);

        void loadNewsPage(int page, String category);

        void onclickNewsItem(News news);
    }

    public interface View {
        void setNewsProfessional(List<Object> news);

        void setNewsFootballBase(List<Object> news);

        void navigateToVideoNewsActivity(News news, int currentPosition);

        void navigateToInfografiaActivity(News news);

        void navigateToNewsDetailsActivity(News news);

        void navigateToGalleryActivity(News news);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError();

    }


}
