package com.millonarios.MillonariosFC.ui.recovery.send.mail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.response.SendEmailResponse;
import com.millonarios.MillonariosFC.ui.recovery.password.RecoveryPasswordActivity;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.di.DaggerSendEmailComponent;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.di.SendEmailModule;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp.SendEmailContract;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp.SendEmailPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendEmailFragment extends Fragment implements SendEmailContract.View {

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.progressBar)
    RelativeLayout progressBar;

    @Inject
    SendEmailPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_email, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        initComponent();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendEmail(inputEmail.getText().toString().trim());
            }
        });
    }

    @Override
    public void success(SendEmailResponse data) {
        ((RecoveryPasswordActivity) getActivity()).pager.setCurrentItem(1, true);
        ((RecoveryPasswordActivity) getActivity()).sendEmail.setEmail(inputEmail.getText().toString().trim());
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
        DaggerSendEmailComponent.builder()
                .appComponent(App.get().component())
                .sendEmailModule(new SendEmailModule(this))
                .build().inject(SendEmailFragment.this);
    }
}
