package com.millonarios.MillonariosFC.ui.chat.requests.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestsFragment;

import dagger.Component;

/**
 * Created by Cesar on 31/01/2018.
 */

@RequestScope
@Component(dependencies = {AppComponent.class}, modules = {RequestModule.class})
public interface RequestComponent {
    void inject(RequestsFragment fragment);
}
