package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.models.PlayerGameActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 15/11/2017.
 */

public class CustomPlayerActivity extends FrameLayout {

    @BindView(R.id.iv_res_activity)
    ImageView ivResActivity;
    @BindView(R.id.tv_time_activity)
    TextView tvTimeActivity;

    public CustomPlayerActivity(@NonNull Context context) {
        super(context);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_player, null);
        addView(root);
        ButterKnife.bind(this, root);
    }

    public CustomPlayerActivity(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_player, null);
        addView(root);
        ButterKnife.bind(this, root);
    }

    public CustomPlayerActivity(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_player, null);
        addView(root);
        ButterKnife.bind(this, root);
    }

    public void setDataActivity(PlayerGameActivity playerGameActivity) {


        int idRes = 0;
        switch (playerGameActivity.getActividad()) {
            case "Entra":
                idRes = R.drawable.entra_icn;
                break;
            case "Sale":
                idRes = R.drawable.sale_icn;
                break;
            case "Tarjeta Roja":
                idRes = R.drawable.roja_icn;
                break;
            case "Tarjeta Amarilla":
                idRes = R.drawable.amr_icn;
                break;
            case "Gol":
                idRes = R.drawable.pelota;
                break;
            case "Lesionado":
                idRes = R.drawable.lesionado_icn;
                break;

            default:
                break;

        }
        Glide.with(this)
                .load("")
                .apply(new RequestOptions().placeholder(idRes).error(idRes))
                .into(ivResActivity);

        tvTimeActivity.setText("'" + playerGameActivity.getMinuto());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomPlayerActivity(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_player, null);
        addView(root);
        ButterKnife.bind(this, root);
    }
}
