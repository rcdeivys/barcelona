package com.BarcelonaSC.BarcelonaApp.ui.recovery.password;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.SendCodeFragment;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.SendEmailFragment;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.password.SendPasswordFragment;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class RecoveryPasswordAdapter extends FragmentStatePagerAdapter {

    private final int tabsNumber = 3;
    private Context context;
    SendEmailFragment sendEmailFragment;
    SendCodeFragment sendCodeFragment;
    SendPasswordFragment sendPasswordFragment;
    String title;

    public RecoveryPasswordAdapter(FragmentManager fm, Context context, SendEmailFragment sendEmailFragment,
                                   SendCodeFragment sendCodeFragment,
                                   SendPasswordFragment sendPasswordFragment) {
        super(fm);
        this.context = context.getApplicationContext();
        this.sendEmailFragment = sendEmailFragment;
        this.sendCodeFragment = sendCodeFragment;
        this.sendPasswordFragment = sendPasswordFragment;

    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return sendEmailFragment;
            case 1:
                return sendCodeFragment;
            case 2:
                return sendPasswordFragment;
            default:
                return sendEmailFragment;
        }
    }


    @Override
    public int getCount() {
        return tabsNumber;
    }
}
