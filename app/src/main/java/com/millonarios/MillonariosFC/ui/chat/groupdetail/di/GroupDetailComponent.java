package com.millonarios.MillonariosFC.ui.chat.groupdetail.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.GroupDetailActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */
@GroupDetailScope
@Component(dependencies = {AppComponent.class}, modules = {GroupDetailModule.class})
public interface GroupDetailComponent {
    void inject(GroupDetailActivity activity);
}
