package com.BarcelonaSC.BarcelonaApp.ui.validate.account;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.AuthActivity;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.di.DaggerValidateAccComponent;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.di.ValidateAccModule;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp.ValidateAccContract;
import com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp.ValidateAccPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 12/6/17.
 */

public class ValidateAccFragment extends Fragment implements ValidateAccContract.View {

    @BindView(R.id.PinEntry)
    PinEntryEditText PinEntry;
    @BindView(R.id.content_progressbar)
    RelativeLayout progressBar;
    @BindView(R.id.btn_resend_pin)
    Button btnResendPin;
    @BindView(R.id.check)
    AppCompatImageView check;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;

    @Inject
    ValidateAccPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_validate_acc, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initComponent();
        PinEntry.focus();
        PinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                try {
                    ((AuthActivity) getActivity()).validateAccItem.setPin(str.toString());
                    presenter.sendCode(((AuthActivity) getActivity()).validateAccItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnResendPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.resendCode(((AuthActivity) getActivity()).validateAccItem);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity) getActivity()).authFragment();
            }
        });
    }

    @Override
    public void success(AuthResponse data) {
        check.setColorFilter(Commons.getColor( R.color.holo_green_dark));
        esperarYCerrar(1000);
    }

    @Override
    public void resendCodeSuccess(String msg) {
        if (getActivity() != null) {

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
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
        if (getActivity() != null) {
            if(error.contains("PIN") && !error.contains(",")){
                error = "Disculpa, PIN incorrecto."
            }
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
    }

    public void initComponent() {
        DaggerValidateAccComponent.builder()
                .appComponent(App.get().component())
                .validateAccModule(new ValidateAccModule(this))
                .build().inject(ValidateAccFragment.this);
    }

    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                ((AuthActivity) getActivity()).navigateToHomeActivity();
                getActivity().finish();
            }
        }, milisegundos);
    }
}
