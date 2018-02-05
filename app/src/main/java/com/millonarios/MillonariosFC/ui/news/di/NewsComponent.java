package com.millonarios.MillonariosFC.ui.news.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.home.menu.news.NewsFragment;

import dagger.Component;

/**
 * Created by Carlos-pc on 04/10/2017.
 */
@NewsScope
@Component(dependencies = {AppComponent.class}, modules = {NewsModule.class})
public interface NewsComponent {
    void inject(NewsFragment fragment);
}
