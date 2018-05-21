package com.losingtimeapps.whitebrand.ui.youchoose;

import com.losingtimeapps.whitebrand.app.di.AppComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.di.ChooseProfileModule;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.di.RankingModule;

import dagger.Component;

/**
 * Created by Pedro Gomez on 18/05/2018.
 */

@YouChooseScope
@Component(dependencies = {AppComponent.class}, modules = {ChooseProfileModule.class, RankingModule.class})
public interface YouChooseComponent {
    void inject(YouChoose fragment);
}

