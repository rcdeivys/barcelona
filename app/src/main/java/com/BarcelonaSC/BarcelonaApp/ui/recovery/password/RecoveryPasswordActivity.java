package com.BarcelonaSC.BarcelonaApp.ui.recovery.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.SendCodeFragment;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.SendEmailFragment;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.password.SendPasswordFragment;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.SendEmail;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

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
        pager.setPagingEnabled(false);
        tabs.setVisibility(View.GONE);
        pager.setAdapter(new RecoveryPasswordAdapter(getSupportFragmentManager(), getActivity(), sendEmailFragment, sendCodeFragment, sendPasswordFragment));
        tabs.setupWithViewPager(pager);
    }
}
