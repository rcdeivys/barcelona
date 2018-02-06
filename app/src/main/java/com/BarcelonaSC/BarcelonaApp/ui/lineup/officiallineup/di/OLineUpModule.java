package com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.di;

import com.BarcelonaSC.BarcelonaApp.app.api.LineUpApi;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.OfficialLineUpFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.mvp.OLineUpPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.mvp.OLineUpModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 13/11/2017.
 */
@Module
public class OLineUpModule {

    private OfficialLineUpFragment fragment;


    public OLineUpModule(OfficialLineUpFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @OLineUpScope
    public OfficialLineUpFragment provideFragment() {
        return fragment;
    }

    @Provides
    @OLineUpScope
    public OLineUpModel provideModel(LineUpApi lineUpApi) {
        return new OLineUpModel(lineUpApi);
    }


    @Provides
    @OLineUpScope
    public OLineUpPresenter providePresenter(OLineUpModel model) {
        return new OLineUpPresenter(fragment, model);
    }

}
