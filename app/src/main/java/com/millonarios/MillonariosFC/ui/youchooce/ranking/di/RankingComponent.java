package com.millonarios.MillonariosFC.ui.youchooce.ranking.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.RankingFragment;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@RankingScope
@Component(dependencies = {AppComponent.class}, modules = {RankingModule.class})
public interface RankingComponent {
    void inject(RankingFragment fragment);
}
