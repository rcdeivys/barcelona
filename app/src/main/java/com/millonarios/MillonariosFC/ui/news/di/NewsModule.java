package com.millonarios.MillonariosFC.ui.news.di;


import com.millonarios.MillonariosFC.app.api.NewsApi;
import com.millonarios.MillonariosFC.ui.home.menu.news.NewsFragment;
import com.millonarios.MillonariosFC.ui.news.mvp.NewsModel;
import com.millonarios.MillonariosFC.ui.news.mvp.NewsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

@Module
public class NewsModule {

    private NewsFragment fragment;

    public NewsModule(NewsFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @NewsScope
    public NewsFragment provideFragment() {
        return fragment;
    }

    @Provides
    @NewsScope
    public NewsModel provideNewsModel(NewsApi newsApi) {
        return new NewsModel(newsApi);
    }


    @Provides
    @NewsScope
    public NewsPresenter providePresenter(NewsModel model) {
        return new NewsPresenter(fragment, model);
    }


}
