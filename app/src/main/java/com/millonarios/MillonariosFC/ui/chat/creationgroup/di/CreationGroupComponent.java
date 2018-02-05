package com.millonarios.MillonariosFC.ui.chat.creationgroup.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.CreationGroupActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */
@CreationGroupScope
@Component(dependencies = {AppComponent.class}, modules = {CreationGroupModule.class})
public interface CreationGroupComponent {
    void inject(CreationGroupActivity creationGroupActivity);
}
