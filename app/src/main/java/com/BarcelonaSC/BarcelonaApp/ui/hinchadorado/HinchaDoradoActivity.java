package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.SuscriptionStatusResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 19/02/2018.
 */

public class HinchaDoradoActivity extends AppCompatActivity {

    @BindView(R.id.btn_continuar)
    Button btn_continuar;

    @BindView(R.id.img_beneficios)
    ImageView img_beneficios;

    @BindView(R.id.iv_closet)
    ImageView ivCloset;

    private String urlImg;
    private final String TAG = HinchaDoradoActivity.class.getSimpleName();
    SuscriptionStatusResponse suscriptionStatusResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_hincha_dorado);
        ButterKnife.bind(this);
        urlImg = ConfigurationManager.getInstance().getConfiguration().getUrlImagenBeneficiosDorados();
        Log.i(TAG, "--->" + urlImg);
        Log.i(TAG, "--->ConfigurationManager: " + ConfigurationManager.getInstance().getConfiguration().toString());
        Glide.with(this)
                .asBitmap()
                .load(urlImg)
                .apply(new RequestOptions().placeholder(R.drawable.logo_transparencia).error(R.drawable.logo_transparencia))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        img_beneficios.setImageBitmap(resource);
                    }
                });
        App.get().component().hinchaDoradoApi().getStatusSuscripcion(SessionManager.getInstance().getSession().getToken()).enqueue(new NetworkCallBack<SuscriptionStatusResponse>() {
            @Override
            public void onRequestSuccess(SuscriptionStatusResponse response) {
                suscriptionStatusResponse = response;
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });
    }

    @OnClick(R.id.btn_continuar)
    public void onClickBtnContinuar() {
        if (suscriptionStatusResponse != null) {
            Intent hinchaDorado = new Intent(this, HinchaDoradoStatusActivity.class);
            if (suscriptionStatusResponse.getStatus().equals("pendiente") || suscriptionStatusResponse.getStatus().equals("exito")) {
                hinchaDorado.putExtra(Constant.Key.DORADO_STATUS_VIEW, suscriptionStatusResponse.getStatus());
                startActivity(hinchaDorado);
            } else {
                startActivity(new Intent(this, HinchaDoradoRegister.class));
            }
        }
        //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.iv_closet)
    public void onViewClicked() {
        finish();
    }
}
