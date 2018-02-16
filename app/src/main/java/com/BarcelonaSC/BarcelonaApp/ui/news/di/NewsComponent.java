package com.BarcelonaSC.BarcelonaApp.ui.news.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.NewsFragment;

import dagger.Component;

/**
 * Created by Carlos-pc on 04/10/2017.
 */
@NewsScope
@Component(dependencies = {AppComponent.class}, modules = {NewsModule.class})
public interface NewsComponent {
    void inject(NewsFragment fragment);
}
