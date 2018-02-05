package com.millonarios.MillonariosFC.ui.chat.friendselection.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.friendselection.FriendSelectionActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */
@FriendSelectionScope
@Component(dependencies = {AppComponent.class}, modules = {FriendSelectionModule.class})
public interface FriendSelectionComponent {
    void inject(FriendSelectionActivity friendSelectionActivity);
}
