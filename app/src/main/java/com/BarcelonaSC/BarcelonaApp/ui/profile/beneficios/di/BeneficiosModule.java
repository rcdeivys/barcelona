package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.di;

import com.millonarios.MillonariosFC.app.api.BeneficiosApi;
import com.millonarios.MillonariosFC.ui.profile.beneficios.BeneficiosActivity;
import com.millonarios.MillonariosFC.ui.profile.beneficios.mvp.BeneficiosModel;
import com.millonarios.MillonariosFC.ui.profile.beneficios.mvp.BeneficiosPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

@Module
public class BeneficiosModule {

    private BeneficiosActivity activity;

    public BeneficiosModule(BeneficiosActivity activity) {
        this.activity = activity;
    }

    @Provides
    @BeneficiosScope
    public BeneficiosModel provideGroupsModel(BeneficiosApi beneficiosApi) {
        return new BeneficiosModel(beneficiosApi);
    }

    @Provides
    @BeneficiosScope
    public BeneficiosPresenter providePresenter(BeneficiosModel model) {
        return new BeneficiosPresenter(activity,model);
    }

}
