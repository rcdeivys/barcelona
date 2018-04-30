package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.ReasonData;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.di.CancelationModule;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.di.DaggerCancelationComponent;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp.CancelationContract;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp.CancelationPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by quint on 2/24/2018.
 */

public class HinchaDoradoCancelationActivity extends BaseActivity implements CancelationContract.View {

    @BindView(R.id.rl_cancelation)
    RecyclerView rlCancelation;
    @BindView(R.id.btn_suscribirme)
    Button btnSuscribirme;
    @BindView(R.id.et_other_reason)
    EditText etOtherReason;

    CancelationAdapter cancelationAdapter;


    @Inject
    CancelationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hincha_dorado_cancelation);
        ButterKnife.bind(this);
        initComponent();
        presenter.onAttach(this);

        rlCancelation.setLayoutManager(new LinearLayoutManager(this));
        if (cancelationAdapter != null) {
            rlCancelation.setAdapter(cancelationAdapter);
        } else {
            presenter.getReason();
        }

        btnSuscribirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelSuscription(etOtherReason.getText().toString());
            }
        });
    }


    public void initComponent() {
        DaggerCancelationComponent.builder()
                .appComponent(App.get().component())
                .cancelationModule(new CancelationModule(this))
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setReasonData(List<ReasonData> reasonData) {

        if (cancelationAdapter == null) {
            cancelationAdapter = new CancelationAdapter(this);

        }
        cancelationAdapter.setData(reasonData);
        rlCancelation.setAdapter(cancelationAdapter);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setRefreshing(boolean state) {

    }

}
