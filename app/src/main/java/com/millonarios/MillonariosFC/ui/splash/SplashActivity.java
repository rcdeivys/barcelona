package com.millonarios.MillonariosFC.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.models.response.ConfigurationResponse;
import com.millonarios.MillonariosFC.ui.home.menu.login.AuthActivity;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.utils.CustomWebView;
import com.millonarios.MillonariosFC.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Carlos on 01/11/2017.
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.wv_juramento)
    CustomWebView wvJuramento;
    @BindView(R.id.rl_splash)
    RelativeLayout rlSplash;
    @BindView(R.id.rl_juramento)
    RelativeLayout rlJuramento;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private PreferenceManager preferenceManager;
    ConfigurationManager configurationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(this);
        wvJuramento.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlJuramento());
        App.get().component().configurationApi().getConfiguration().enqueue(new NetworkCallBack<ConfigurationResponse>() {
            @Override
            public void onRequestSuccess(ConfigurationResponse response) {
                wvJuramento.loadUrl(response.getData().getUrlJuramento());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });

    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        preferenceManager.setBoolean(Constant.Key.FIRST_TIME_OATH, false);
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }
}
