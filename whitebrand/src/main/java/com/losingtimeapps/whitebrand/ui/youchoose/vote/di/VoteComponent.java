package com.losingtimeapps.whitebrand.ui.youchoose.vote.di;

import com.losingtimeapps.whitebrand.app.di.AppComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.Vote;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@VoteScope
@Component(dependencies = {AppComponent.class}, modules = {VoteModule.class})
public interface VoteComponent {
    void inject(Vote fragment);
}
