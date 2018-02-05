package com.millonarios.MillonariosFC.ui.virtualreality.di;


import com.millonarios.MillonariosFC.app.api.VRApi;
import com.millonarios.MillonariosFC.ui.virtualreality.mvp.VRModel;
import com.millonarios.MillonariosFC.ui.virtualreality.mvp.VRPresenter;
import com.millonarios.MillonariosFC.ui.virtualreality.VRFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

@Module
public class VRModule {

    private VRFragment fragment;

    public VRModule(VRFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @VRScope
    public VRFragment provideFragment() {
        return fragment;
    }

    @Provides
    @VRScope
    public VRModel provideModel(VRApi vrApi) {
        return new VRModel(vrApi);
    }


    @Provides
    @VRScope
    public VRPresenter providePresenter(VRModel model) {
        return new VRPresenter(fragment, model);
    }


}
