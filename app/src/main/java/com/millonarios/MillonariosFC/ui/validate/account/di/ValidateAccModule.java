package com.millonarios.MillonariosFC.ui.validate.account.di;

import com.millonarios.MillonariosFC.app.api.AuthApi;
import com.millonarios.MillonariosFC.app.api.ProfileApi;
import com.millonarios.MillonariosFC.ui.validate.account.ValidateAccFragment;
import com.millonarios.MillonariosFC.ui.validate.account.mvp.ValidateAccContract;
import com.millonarios.MillonariosFC.ui.validate.account.mvp.ValidateAccModel;
import com.millonarios.MillonariosFC.ui.validate.account.mvp.ValidateAccPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 12/6/17.
 */

@Module
public class ValidateAccModule {

    private ValidateAccFragment validateAccFragment;

    public ValidateAccModule(ValidateAccFragment validateAccFragment) {
        this.validateAccFragment = validateAccFragment;
    }

    @Provides
    @ValidateAccScope
    public ValidateAccContract.View provideActivity() {
        return validateAccFragment;
    }

    @Provides
    @ValidateAccScope
    public ValidateAccModel providModel(AuthApi authApi, ProfileApi profileApi) {
        return new ValidateAccModel(authApi, profileApi);
    }

    @Provides
    @ValidateAccScope
    public ValidateAccPresenter providePresenter(ValidateAccModel model) {
        return new ValidateAccPresenter(validateAccFragment, model);
    }
}
