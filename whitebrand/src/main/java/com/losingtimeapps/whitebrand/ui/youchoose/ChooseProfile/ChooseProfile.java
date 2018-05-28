package com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.di.ChooseProfileModule;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.di.DaggerChooseProfileComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfilePresenter;

import javax.inject.Inject;

/**
 * Created by Pedro Gomez on 21/05/2018.
 */

public class ChooseProfile {


    @Inject
    public ChooseProfilePresenter chooseProfilePresenter;


    public ChooseProfilePresenter initProfile(ChooseProfileContract.View view) {
        if (chooseProfilePresenter == null) {
            DaggerChooseProfileComponent.builder()
                    .appComponent(WhiteBrand.getComponent())
                    .chooseProfileModule(new ChooseProfileModule(view))
                    .build().inject(this);
        }
        return chooseProfilePresenter;

    }

    public int sumar() {
        return 8;
    }
}
