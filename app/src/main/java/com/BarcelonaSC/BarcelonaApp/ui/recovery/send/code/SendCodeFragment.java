package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.di.SendCodeModule;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv.SendCodePresenter;
import com.BarcelonaSC.BarcelonaApp.models.response.SendCodeResponse;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.password.RecoveryPasswordActivity;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.di.DaggerSendCodeComponent;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv.SendCodeContract;
import com.alimuzaffar.lib.pin.PinEntryEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendCodeFragment extends Fragment implements SendCodeContract.View {

    @BindView(R.id.pinEntry)
    PinEntryEditText pinEntry;
    @BindView(R.id.progressBar)
    RelativeLayout progressBar;

    @Inject
    SendCodePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_code, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initComponent();
        pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                presenter.sendCode(((RecoveryPasswordActivity) getActivity()).sendEmail.getEmail(), str.toString());
            }
        });
    }

    @Override
    public void success(AuthItem data) {
        getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToastError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    public void initComponent() {
        DaggerSendCodeComponent.builder()
                .appComponent(App.get().component())
                .sendCodeModule(new SendCodeModule(this))
                .build().inject(SendCodeFragment.this);
    }

}