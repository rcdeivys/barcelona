package com.BarcelonaSC.BarcelonaApp.ui.chat.requests.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.RequestsFragment;

import dagger.Component;

/**
 * Created by Cesar on 31/01/2018.
 */

@RequestScope
@Component(dependencies = {AppComponent.class}, modules = {RequestModule.class})
public interface RequestComponent {
    void inject(RequestsFragment fragment);
}
