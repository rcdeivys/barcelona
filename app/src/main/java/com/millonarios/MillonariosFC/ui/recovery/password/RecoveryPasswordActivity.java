package com.millonarios.MillonariosFC.ui.recovery.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.ui.recovery.send.code.SendCodeFragment;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.SendEmailFragment;
import com.millonarios.MillonariosFC.ui.recovery.send.password.SendPasswordFragment;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.models.SendEmail;
import com.millonarios.MillonariosFC.utils.CustomTabLayout;
import com.millonarios.MillonariosFC.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class RecoveryPasswordActivity extends BaseActivity {

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    public CustomViewPager pager;

    SendEmailFragment sendEmailFragment;
    SendCodeFragment sendCodeFragment;
    SendPasswordFragment sendPasswordFragment;

    public SendEmail sendEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        ButterKnife.bind(this);
        initViewPager();
        sendEmail = new SendEmail("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initViewPager() {
        sendEmailFragment = new SendEmailFragment();
        sendCodeFragment = new SendCodeFragment();
        sendPasswordFragment = new SendPasswordFragment();

        tabs.setVisibility(View.GONE);
        pager.setAdapter(new RecoveryPasswordAdapter(getSupportFragmentManager(), getActivity(), sendEmailFragment, sendCodeFragment, sendPasswordFragment));
        tabs.setupWithViewPager(pager);
    }
}
