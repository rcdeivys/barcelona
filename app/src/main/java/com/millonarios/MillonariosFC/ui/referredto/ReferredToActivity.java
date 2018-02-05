package com.millonarios.MillonariosFC.ui.referredto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.ui.referredto.fragments.ReferredListFragment;
import com.millonarios.MillonariosFC.ui.referredto.fragments.ReferredToFragment;
import com.millonarios.MillonariosFC.utils.PreferenceManager;

import butterknife.BindView;

/**
 * Created by RYA-Laptop on 04/01/2018.
 */

public class ReferredToActivity extends BaseActivity {

    private static String TAG = ReferredToActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_referred);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ref_container, ReferredToFragment.instance(false))
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.ref_container);
        if (f instanceof ReferredToFragment) {
            this.finish();
        } else if (f instanceof ReferredListFragment) {
            this.finish();
        } else {
            this.finish();
        }
    }

}