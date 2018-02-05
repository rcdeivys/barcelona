package com.millonarios.MillonariosFC.ui.lineup.officiallineup.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.lineup.officiallineup.OfficialLineUpFragment;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@OLineUpScope
@Component(dependencies = {AppComponent.class}, modules = {OLineUpModule.class})
public interface OLineUpComponent {
    void inject(OfficialLineUpFragment fragment);
}
