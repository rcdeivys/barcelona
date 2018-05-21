package com.losingtimeapps.whitebrand.ui.youchoose.vote;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.di.DaggerVoteComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.di.VoteModule;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VoteContract;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VotePresenter;

import javax.inject.Inject;

/**
 * Created by Pedro Gomez on 21/05/2018.
 */

public class Vote {

    @Inject
    public VotePresenter votePresenter;


    public VotePresenter initVote(VoteContract.View view) {
        if (votePresenter == null) {
            DaggerVoteComponent.builder()
                    .appComponent(WhiteBrand.getComponent())
                    .voteModule(new VoteModule(view))
                    .build().inject(this);
        }
        return votePresenter;

    }
}
