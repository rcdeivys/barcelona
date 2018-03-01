package com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.di;

import com.BarcelonaSC.BarcelonaApp.app.api.YouChooseApi;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.VoteFragment;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.mvp.VoteModel;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.mvp.VotePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 14/10/2017.
 */

@Module
public class VoteModule {

    private VoteFragment fragment;


    public VoteModule(VoteFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @VoteScope
    public VoteModel provideModel(YouChooseApi youChooseApi) {
        return new VoteModel(youChooseApi);
    }


    @Provides
    @VoteScope
    public VotePresenter providePresenter(VoteModel model) {

        return new VotePresenter(fragment, model);

    }

}
