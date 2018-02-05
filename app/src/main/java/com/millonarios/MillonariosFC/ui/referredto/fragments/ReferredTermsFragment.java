package com.millonarios.MillonariosFC.ui.referredto.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RYA-Laptop on 04/01/2017.
 */

public class ReferredTermsFragment extends DialogFragment {

    @BindView(R.id.tutorial_wb)
    WebView tutorialWb;
    @BindView(R.id.btn_close)
    ImageButton btnClose;

    PreferenceManager preferenceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_referred);
        dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, dialogo);

        preferenceManager = new PreferenceManager(getContext());

        tutorialWb.getSettings().setJavaScriptEnabled(true);
        tutorialWb.getSettings().setPluginState(WebSettings.PluginState.ON);
        tutorialWb.getSettings().setBuiltInZoomControls(false);
        tutorialWb.getSettings().setSupportZoom(false);
        tutorialWb.loadUrl(preferenceManager.getString("politicas", ConfigurationManager.getInstance().getConfiguration().getTermReferidos()));
        tutorialWb.setWebChromeClient(new WebChromeClient());

        return dialogo;
    }

    @OnClick({R.id.btn_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
        }
    }

}