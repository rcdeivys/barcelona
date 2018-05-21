package com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.di;


import com.losingtimeapps.whitebrand.app.api.YouChooseApi;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileModel;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfilePresenter;
import com.losingtimeapps.whitebrand.ui.youchoose.YouChooseScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 11/10/2017.
 */

@Module
public class ChooseProfileModule {

    private  ChooseProfileContract.View fragment;


    public ChooseProfileModule(ChooseProfileContract.View fragment) {
        this.fragment = fragment;
    }

    @Provides
    @YouChooseScope
    public ChooseProfileModel provideModel(YouChooseApi youChooseApi) {
        return new ChooseProfileModel(youChooseApi);
    }


    @Provides
    @YouChooseScope
    public ChooseProfilePresenter providePresenter(ChooseProfileModel model) {

        return new ChooseProfilePresenter(fragment, model);

    }

}
