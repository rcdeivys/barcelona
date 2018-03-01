package com.BarcelonaSC.BarcelonaApp.ui.validate.account.di;

import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.ValidateAccFragment;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp.ValidateAccContract;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp.ValidateAccModel;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp.ValidateAccPresenter;

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
