package com.BarcelonaSC.BarcelonaApp.ui.news.di;


import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsModel;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;

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