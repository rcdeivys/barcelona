package com.BarcelonaSC.BarcelonaApp.ui.news.news_single.di;

import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.NewsSingleActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp.NewsSingleModel;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp.NewsSinglePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

@Module
public class NewsSingleModule {

    private NewsSingleActivity activity;

    public NewsSingleModule(NewsSingleActivity activity) {
        this.activity = activity;
    }

    @Provides
    @NewsSingleScope
    public NewsSingleActivity provideActivity() {
        return activity;
    }

    @Provides
    @NewsSingleScope
    public NewsSingleModel provideNewsSingleModel(NewsApi api) {
        return new NewsSingleModel(api);
    }

    @Provides
    @NewsSingleScope
    public NewsSinglePresenter provideNewsSinglePresenter(NewsSingleModel model) {
        return new NewsSinglePresenter(activity, model);
    }

}