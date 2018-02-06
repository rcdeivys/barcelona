package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.ChooseProfileFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@ChooseProfileScope
@Component(dependencies = {AppComponent.class}, modules = {ChooseProfileModule.class})
public interface ChooseProfileComponent {
    void inject(ChooseProfileFragment fragment);
}
