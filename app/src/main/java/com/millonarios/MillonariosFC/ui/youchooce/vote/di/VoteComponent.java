package com.millonarios.MillonariosFC.ui.youchooce.vote.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.RankingFragment;
import com.millonarios.MillonariosFC.ui.youchooce.vote.VoteFragment;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@VoteScope
@Component(dependencies = {AppComponent.class}, modules = {VoteModule.class})
public interface VoteComponent {
    void inject(VoteFragment fragment);
}
