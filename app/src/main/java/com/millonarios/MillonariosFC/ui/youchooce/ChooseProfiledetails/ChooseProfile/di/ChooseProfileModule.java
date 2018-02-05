package com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.di;


import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.app.api.YouChooseApi;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.ChooseProfileFragment;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp.ChooseProfileModel;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp.ChooseProfilePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 11/10/2017.
 */

@Module
public class ChooseProfileModule {

    private ChooseProfileFragment fragment;


    public ChooseProfileModule(ChooseProfileFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ChooseProfileScope
    public ChooseProfileModel provideModel(YouChooseApi youChooseApi) {
        return new ChooseProfileModel(youChooseApi);
    }


    @Provides
    @ChooseProfileScope
    public ChooseProfilePresenter providePresenter(ChooseProfileModel model) {

        return new ChooseProfilePresenter(fragment, model);

    }

}
