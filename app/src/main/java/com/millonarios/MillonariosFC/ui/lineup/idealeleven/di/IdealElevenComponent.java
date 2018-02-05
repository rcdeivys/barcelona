package com.millonarios.MillonariosFC.ui.lineup.idealeleven.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.lineup.idealeleven.IdealElevenFragment;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@IdealElevenScope
@Component(dependencies = {AppComponent.class}, modules = {IdealElevenModule.class})
public interface IdealElevenComponent {
    void inject(IdealElevenFragment fragment);
}
