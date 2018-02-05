package com.millonarios.MillonariosFC.ui.home.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.commons.DBController;
import com.millonarios.MillonariosFC.commons.Services.NotificationSettingController;
import com.millonarios.MillonariosFC.models.ValidateAccItem;
import com.millonarios.MillonariosFC.ui.login.fragments.AuthFragment;
import com.millonarios.MillonariosFC.ui.register.fragments.RegisterFragment;
import com.millonarios.MillonariosFC.ui.validate.account.ValidateAccFragment;

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
