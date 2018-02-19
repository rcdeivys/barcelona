package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.MonumentalRankingFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 18/02/2018.
 */

@MonumentalRankScope
@Component(dependencies = {AppComponent.class}, modules = {MonumentalRankModule.class})
public interface MonumentalRankComponent {
    void inject(MonumentalRankingFragment fragment);
}