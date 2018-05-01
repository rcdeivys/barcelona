package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.BeneficiosActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

@BeneficiosScope
@Component(dependencies = {AppComponent.class}, modules = {BeneficiosModule.class})
public interface BeneficiosComponent {

    void inject(BeneficiosActivity activity);

}
