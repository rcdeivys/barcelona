package com.BarcelonaSC.BarcelonaApp.commons;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity implements CustomVideoView.CustomVideoViewOnListener{

    @BindView(R.id.custom_video_view)
    CustomVideoView customVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        customVideoView.setCustomVideoViewOnListener(this);
        customVideoView.setVideoUrl(getIntent().getStringExtra(Constant.Video.URL), getIntent().getIntExtra(Constant.Video.CURRENT_POSITION, 0), false);

        customVideoView.setFullScreen(true);
        customVideoView.setButtonFullScreen(R.drawable.ic_media_fullscreen_exit, Commons.getColor(R.color.white), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.btn_back)
    public void btnBack() {
        finish();
    }

    @Override
    public void onPrepared() {
        customVideoView.start();
        if (customVideoView.getVideoHeight() > customVideoView.getVideoWidth()) {
            customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void videoIsDorado() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (customVideoView.getVideoHeight() > customVideoView.getVideoWidth()) {
                customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (customVideoView.getVideoHeight() > customVideoView.getVideoWidth()) {
                customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                customVideoView.setLayoutParamsVideo(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private void scaleVideo () {

    }
}
