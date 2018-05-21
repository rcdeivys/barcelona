package com.losingtimeapps.whitebrand.ui.youchoose.vote.di;

import com.losingtimeapps.whitebrand.app.api.YouChooseApi;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VoteContract;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VoteModel;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VotePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 14/10/2017.
 */

@Module
public class VoteModule {

    private VoteContract.View fragment;


    public VoteModule(VoteContract.View fragment) {
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
