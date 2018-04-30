package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.vr.sdk.widgets.video.VrVideoView;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.paymentwebviews.PayUPaymentActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by cesar on 24/2/2018.
 */

public class HinchaDoradoStatusActivity extends AppCompatActivity implements PayUPaymentActivity.OnItemClickListener {

    @Nullable
    @BindView(R.id.btn_continuar_aproved)
    Button btn_continuar_aproved;
    @Nullable
    @BindView(R.id.btn_continuar_processs)
    Button btn_continuar_processs;
    @Nullable
    @BindView(R.id.declined_btn_payu)
    Button declined_btn_payu;
    @Nullable
    @BindView(R.id.fl_container_back)
    FrameLayout flContainerBack;
    @Nullable
    @BindView(R.id.videoview_bienvenida)
    CustomVideoView videoView;

    SuscripcionData suscripcionData = null;
    private boolean isPaused = false;

    private String TAG = HinchaDoradoStatusActivity.class.getSimpleName();
    String TAG_CLASS;
    VrVideoView.Options options = new VrVideoView.Options();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            TAG_CLASS = getIntent().getExtras().getString(Constant.Key.DORADO_STATUS_VIEW, "");
            Log.i(TAG, "--->" + getIntent().getExtras().getString(Constant.Key.DORADO_STATUS_VIEW, ""));
            suscripcionData = (SuscripcionData) getIntent().getSerializableExtra(Constant.Key.DORADO_STATUS_SUSCRIPCION);
            switch (TAG_CLASS) {
                case HinchaDoradoConsts.TAG_STATUS_exito: {
                    setContentView(R.layout.activity_transaction_approved);
                    ButterKnife.bind(this);
                    settingDoradoToUserPreferences();
                    initVideoBienvenida();
                    new PreferenceManager().setBoolean(Constant.Key.FIRST_TIME_DORADO, false);
                    break;
                }
                case HinchaDoradoConsts.TAG_STATUS_pendiente: {
                    setContentView(R.layout.activity_transaction_processing);
                    ButterKnife.bind(this);
                    break;
                }
                case HinchaDoradoConsts.TAG_STATUS_fallo: {
                    setContentView(R.layout.activity_transaction_declined);
                    ButterKnife.bind(this);
                    break;
                }
            }

        }

    }

    @Optional
    @OnClick(R.id.fl_container_back)
    public void onBackButtonPressed() {
        switch (TAG_CLASS) {
            case HinchaDoradoConsts.TAG_STATUS_exito: {
                onClickBtnContinuarAproved();
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_pendiente: {
                onClickBtnContinuarProcess();
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_fallo: {
                finish();
                break;
            }
        }
    }

    private void settingDoradoToUserPreferences() {
        UserItem userItem = SessionManager.getInstance().getUser();
        userItem.setDorado(true);
        SessionManager.getInstance().setUser(userItem);
    }

    private void initVideoBienvenida() {
        if (videoView != null) {
            videoView.setVideoUrl(ConfigurationManager.getInstance().getConfiguration().getVideoDeBienvenidaDorados(), 0, false);
            videoView.start();
            videoView.controllerVisible(View.GONE);
            videoView.setFullScreen(true);
        }
    }

    @Override
    public void onBackPressed() {
        switch (TAG_CLASS) {
            case HinchaDoradoConsts.TAG_STATUS_exito: {
                onClickBtnContinuarAproved();
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_pendiente: {
                onClickBtnContinuarProcess();
                break;
            }
            case HinchaDoradoConsts.TAG_STATUS_fallo: {
                finish();
                break;
            }
        }
    }

    @Optional
    @OnClick(R.id.fl_container_back)
    public void onClickFlContainerBack() {
        onBackPressed();
    }

    @Optional
    @OnClick(R.id.declined_btn_payu)
    public void onClickBtnPayuDeclined() {
        finish();
    }

    @Optional
    @OnClick(R.id.btn_continuar_processs)
    public void onClickBtnContinuarProcess() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Optional
    @OnClick(R.id.btn_continuar_aproved)
    public void onClickBtnContinuarAproved() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("doradoGod", 13);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onClickWebView(String TAG) {

    }


    @Override
    protected void onDestroy() {
        stopAndDestroyVideo();
        super.onDestroy();
    }

    private void stopAndDestroyVideo() {
        // Destroy the widget and free memory.
        if (videoView != null) {
            videoView = null;
        }
    }
}
