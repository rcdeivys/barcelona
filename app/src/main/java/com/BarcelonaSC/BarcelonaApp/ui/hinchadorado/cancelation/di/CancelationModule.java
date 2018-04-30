package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.di;

import com.BarcelonaSC.BarcelonaApp.app.api.HinchaDoradoApi;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.HinchaDoradoCancelationActivity;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp.CancelationModel;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp.CancelationPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 13/11/2017.
 */
@Module
public class CancelationModule {

    private HinchaDoradoCancelationActivity activity;


    public CancelationModule(HinchaDoradoCancelationActivity activity) {
        this.activity = activity;
    }

    @Provides
    @CancelationScope
    public HinchaDoradoCancelationActivity provideActivity() {
        return activity;
    }

    @Provides
    @CancelationScope
    public CancelationModel provideModel(HinchaDoradoApi hinchaDoradoApi) {
        return new CancelationModel(hinchaDoradoApi);
    }


    @Provides
    @CancelationScope
    public CancelationPresenter providePresenter(CancelationModel model) {
        return new CancelationPresenter(activity, model);
    }

}
