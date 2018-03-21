package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.MonumentalNewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp.MonumentalNewsModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp.MonumentalNewsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 15/02/2018.
 */

@Module
public class MonumentalNewsModule {

    private MonumentalNewsFragment fragment;

    public MonumentalNewsModule(MonumentalNewsFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MonumentalNewsScope
    public MonumentalNewsFragment provideMonumentalNews() {
        return fragment;
    }

    @Provides
    @MonumentalNewsScope
    public MonumentalNewsModel provideMonumentalNewsModel(MonumentalApi newsApi) {
        return new MonumentalNewsModel(newsApi);
    }

    @Provides
    @MonumentalNewsScope
    public MonumentalNewsPresenter provideMonumentalNewsPresenter(MonumentalNewsModel model) {
        return new MonumentalNewsPresenter(fragment, model);
    }

}