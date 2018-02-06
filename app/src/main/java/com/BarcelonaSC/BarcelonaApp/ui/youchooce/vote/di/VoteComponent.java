package com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.VoteFragment;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@VoteScope
@Component(dependencies = {AppComponent.class}, modules = {VoteModule.class})
public interface VoteComponent {
    void inject(VoteFragment fragment);
}
