package com.millonarios.MillonariosFC.ui.lineup.idealeleven.di;

import com.millonarios.MillonariosFC.ui.lineup.idealeleven.mvp.IdealElevenModel;
import com.millonarios.MillonariosFC.app.api.LineUpApi;
import com.millonarios.MillonariosFC.ui.lineup.idealeleven.IdealElevenFragment;
import com.millonarios.MillonariosFC.ui.lineup.idealeleven.mvp.IdealElevenPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 13/11/2017.
 */
@Module
public class IdealElevenModule {

    private IdealElevenFragment fragment;


    public IdealElevenModule(IdealElevenFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @IdealElevenScope
    public IdealElevenFragment provideFragment() {
        return fragment;
    }

    @Provides
    @IdealElevenScope
    public IdealElevenModel provideModel(LineUpApi lineUpApi) {
        return new IdealElevenModel(lineUpApi);
    }


    @Provides
    @IdealElevenScope
    public IdealElevenPresenter providePresenter(IdealElevenModel model) {
        return new IdealElevenPresenter(fragment, model);
    }

}
