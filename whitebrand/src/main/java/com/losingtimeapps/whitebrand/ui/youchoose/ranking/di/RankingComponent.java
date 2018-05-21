package com.losingtimeapps.whitebrand.ui.youchoose.ranking.di;


import com.losingtimeapps.whitebrand.app.di.AppComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.Ranking;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@RankingScope
@Component(dependencies = {AppComponent.class}, modules = {RankingModule.class})
public interface RankingComponent {
    void inject(Ranking fragment);
}
