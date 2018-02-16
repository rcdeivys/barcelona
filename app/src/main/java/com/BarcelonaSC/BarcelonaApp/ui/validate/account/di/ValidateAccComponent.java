package com.BarcelonaSC.BarcelonaApp.ui.validate.account.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.ValidateAccFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 12/6/17.
 */

@ValidateAccScope
@Component(dependencies = {AppComponent.class}, modules = {ValidateAccModule.class})
public interface ValidateAccComponent {
    void inject(ValidateAccFragment validateAccFragment);
}
