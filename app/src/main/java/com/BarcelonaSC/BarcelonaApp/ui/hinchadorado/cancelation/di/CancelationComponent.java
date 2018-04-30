package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.HinchaDoradoCancelationActivity;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@CancelationScope
@Component(dependencies = {AppComponent.class}, modules = {CancelationModule.class})
public interface CancelationComponent {
    void inject(HinchaDoradoCancelationActivity activity);
}
