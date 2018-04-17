package com.BarcelonaSC.BarcelonaApp.ui.news.news_single.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.NewsSingleActivity;

import dagger.Component;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

@NewsSingleScope
@Component(dependencies = {AppComponent.class}, modules = {NewsSingleModule.class})
public interface NewsSingleComponent {
    void inject(NewsSingleActivity activity);
}