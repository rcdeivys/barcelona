package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.IdealElevenRankingFragment;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@IdealElevenRankingScope
@Component(dependencies = {AppComponent.class}, modules = {IdealElevenRankingModule.class})
public interface IdealElevenRankingComponent {
    void inject(IdealElevenRankingFragment fragment);
}
