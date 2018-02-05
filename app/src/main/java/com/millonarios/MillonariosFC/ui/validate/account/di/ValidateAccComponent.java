package com.millonarios.MillonariosFC.ui.validate.account.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.register.di.RegisterScope;
import com.millonarios.MillonariosFC.ui.validate.account.ValidateAccFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 12/6/17.
 */

@ValidateAccScope
@Component(dependencies = {AppComponent.class}, modules = {ValidateAccModule.class})
public interface ValidateAccComponent {
    void inject(ValidateAccFragment validateAccFragment);
}
