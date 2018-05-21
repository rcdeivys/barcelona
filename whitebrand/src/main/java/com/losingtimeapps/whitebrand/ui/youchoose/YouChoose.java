package com.losingtimeapps.whitebrand.ui.youchoose;

import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.ChooseProfile;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.Ranking;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingContract;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.Vote;
import com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp.VoteContract;

/**
 * Created by Pedro Gomez on 21/05/2018.
 */

public class YouChoose {

    public void initRanking(RankingContract.View view) {
        new Ranking().initRanking(view);
    }

    public void initChooseProfile(ChooseProfileContract.View view) {
        new ChooseProfile().initProfile(view);
    }

    public void initVote(VoteContract.View view) {
        new Vote().initVote(view);
    }
}
