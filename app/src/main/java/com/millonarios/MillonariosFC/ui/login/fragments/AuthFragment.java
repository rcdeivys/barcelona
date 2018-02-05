package com.millonarios.MillonariosFC.ui.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.millonarios.MillonariosFC.models.AuthItem;
import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.ui.home.menu.login.AuthActivity;
import com.millonarios.MillonariosFC.ui.home.menu.login.LoginActivity;
import com.millonarios.MillonariosFC.ui.login.ConstantsAuth;
import com.millonarios.MillonariosFC.ui.login.di.DaggerLoginComponent;
import com.millonarios.MillonariosFC.ui.login.di.LoginModule;
import com.millonarios.MillonariosFC.ui.login.mvp.LoginContract;
import com.millonarios.MillonariosFC.ui.login.mvp.LoginPresenter;
import com.millonarios.MillonariosFC.ui.recovery.password.RecoveryPasswordActivity;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardojpr on 11/9/17.
 */

public class AuthFragment extends Fragment implements LoginContract.View {

    private String source = null;
    @BindView(R.id.btn_signin)
    Button btnSignIn;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.btn_google)
    Button btnGoogle;
    @BindView(R.id.btn_facebook)
    Button btnFacebook;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.progressBar)
    RelativeLayout progressBar;
    @BindView(R.id.recovery_password)
    FCMillonariosTextView recovery;

    @Inject
    LoginPresenter presenter;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        Commons.hideKeyboard(getActivity());

        initComponent();

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), RecoveryPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity) getActivity()).registerFragment();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = ConstantsAuth.SOURCE_FACEBOOK;
                initLoginActivity();
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = ConstantsAuth.SOURCE_GOOGLE_PLUS;
                initLoginActivity();
            }
        });
    }

    private void validateForm() {
        if (!etEmail.getText().toString().isEmpty() && !etPass.getText().toString().isEmpty()) {
            if (etEmail.getText().toString().contains("@")) {
                signIn();
            } else {
                Toast.makeText(getActivity(), "La dirección de correo no es válida", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "El correo y la contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        user = new User(etEmail.getText().toString(), etPass.getText().toString());
        presenter.loadLogin(user);
    }

    public void initComponent() {
        DaggerLoginComponent.builder()
                .appComponent(App.get().component())
                .loginModule(new LoginModule(this))
                .build().inject(AuthFragment.this);
    }

    private void initLoginActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("source", source);
                startActivityForResult(intent, ConstantsAuth.CODE_ACCESS_REQUEST_SOCIAL);
            }
        }, 1000);
    }

    @Override
    public void setLogin(AuthItem data) {
        ((AuthActivity) getActivity()).sessionManager.setSession(data);
        ((AuthActivity) getActivity()).navigateToHomeActivity();
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
        if (getActivity() != null) {
            if (error.equals("La cuenta aun no ha sido confirmada")) {
                ((AuthActivity) getActivity()).validateFragment();
                ((AuthActivity)getActivity()).validateAccItem.setEmail(etEmail.getText().toString().trim());
            }
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LoginActivity.CODE_ACCESS_GRANTED) {
            presenter.loadLoginSocial((User) data.getSerializableExtra("user"));
        }
    }
}
