package com.BarcelonaSC.BarcelonaApp.ui.virtualreality.di;


import com.BarcelonaSC.BarcelonaApp.app.api.VRApi;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.mvp.VRModel;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.mvp.VRPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VRFragment;

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
