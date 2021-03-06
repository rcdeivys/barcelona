package com.BarcelonaSC.BarcelonaApp.ui.referredto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.ReferredListFragment;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.ReferredToFragment;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

/**
 * Created by RYA-Laptop on 04/01/2018.
 */

public class ReferredToActivity extends BaseActivity {

    private static String TAG = ReferredToActivity.class.getSimpleName();

    public static final String ACCEPT = "accept";
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_referred);

        preferenceManager = new PreferenceManager(getActivity());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ref_container, ReferredToFragment.instance(false))
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.ref_container);
        if (f instanceof ReferredToFragment) {
            if (preferenceManager.getBoolean(ACCEPT, false)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ref_container, new ReferredListFragment())
                        .commit();
            } else {
                this.finish();
            }
        } else if (f instanceof ReferredListFragment) {
            this.finish();
        } else {
            this.finish();
        }
    }

}