package com.BarcelonaSC.BarcelonaApp.ui.home.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.commons.DBController;
import com.BarcelonaSC.BarcelonaApp.commons.Services.NotificationSettingController;
import com.BarcelonaSC.BarcelonaApp.models.ValidateAccItem;
import com.BarcelonaSC.BarcelonaApp.ui.login.fragments.AuthFragment;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.ValidateAccFragment;

import butterknife.ButterKnife;

/**
 * Created by leonardojpr on 11/9/17.
 */

public class AuthActivity extends BaseActivity {

    AuthFragment authFragment;
    RegisterFragment registerFragment;
    ValidateAccFragment validateAccFragment;
    public ValidateAccItem validateAccItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        validateAccItem = new ValidateAccItem();
        authFragment();
        DBController.getControler().createDB(getActivity());

        NotificationSettingController.initSuscribeTopicAll(getBaseContext());

    }

    public void authFragment() {
        authFragment = (AuthFragment)
                getSupportFragmentManager().findFragmentByTag(AuthFragment.class.getSimpleName());
        if (authFragment == null) {
            authFragment = new AuthFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in,
                    R.anim.top_out).add(R.id.content_fragment, authFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in,
                    R.anim.top_out).replace(R.id.content_fragment, authFragment).commit();
        }
    }

    public void registerFragment() {
        registerFragment = (RegisterFragment)
                getSupportFragmentManager().findFragmentByTag(RegisterFragment.class.getSimpleName());
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in,
                    R.anim.top_out).add(R.id.content_fragment, registerFragment).addToBackStack(RegisterFragment.class.getSimpleName()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in,
                    R.anim.top_out).replace(R.id.content_fragment, registerFragment).commit();
        }
    }

    public void validateFragment() {
        validateAccFragment = (ValidateAccFragment) getSupportFragmentManager().findFragmentByTag(ValidateAccFragment.class.getSimpleName());
        if (validateAccFragment == null) {
            validateAccFragment = new ValidateAccFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in, R.anim.top_out).add(R.id.content_fragment, validateAccFragment).addToBackStack(ValidateAccFragment.class.getSimpleName()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_in,
                    R.anim.top_out).replace(R.id.content_fragment, validateAccFragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}