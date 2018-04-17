package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sprylab.android.widget.TextureVideoView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 1/15/18.
 */

public class CustomVideoView extends RelativeLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener {

    public static final String TAG = CustomVideoView.class.getSimpleName();

    private static final String STATE_IS_PAUSED = "isPaused";
    private static final String STATE_VIDEO_DURATION = "videoDuration";
    private static final String STATE_PROGRESS_TIME = "progressTime";

    @BindView(R.id.video_view)
    public TextureVideoView videoView;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.fullscreen)
    AppCompatImageView btnFullScreen;
    @BindView(R.id.content_video)
    RelativeLayout contentVideo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.time)
    FCMillonariosTextView textTime;
    @BindView(R.id.img_news)
    public ImageView imgNews;
    @BindView(R.id.content_controller)
    LinearLayout contentController;
    @BindView(R.id.content_video_view)
    RelativeLayout contentVideoView;
    int height;
    int width;

    String frame;

    boolean fullScreen = false;

    boolean isDorado = false;

    public CustomVideoViewOnListener customVideoViewOnListener;

    String url = "";

    boolean error = false;

    public CustomVideoView(Context context) {
        super(context);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.custom_video_view, null);
        addView(root);

        ButterKnife.bind(this, root);

        play.setVisibility(GONE);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnInfoListener(this);
        videoView.setOnErrorListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        progressBar.setVisibility(View.VISIBLE);

        videoView.stopPlayback();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        play.setVisibility(VISIBLE);
        videoView.seekTo(0);
        seekBar.setProgress(0);
        //  imgNews.setVisibility(VISIBLE);
        if (fullScreen) {
            imgNews.setVisibility(INVISIBLE);
        }
        time(0);

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {


        // setImage(getContext(), url);
        progressBar.setVisibility(View.GONE);

        setVideoHeight(mediaPlayer.getVideoHeight());
        setVideoWidth(mediaPlayer.getVideoWidth());

        contentVideo.setVisibility(View.VISIBLE);

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.postDelayed(onEverySecond, 1000);
        play.setVisibility(VISIBLE);

        contentVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (error)
                    Toast.makeText(getContext(), "No se pudo reproducir el video", Toast.LENGTH_LONG).show();
              /*  if (!SessionManager.getInstance().getUser().isDorado() && isDorado) {
                    customVideoViewOnListener.videoIsDorado();
                    return;
                }*/
                if (!videoView.isPlaying()) {
                    seekBar.postDelayed(onEverySecond, 1000);
                    videoView.start();
                    imgNews.setVisibility(INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    play.setVisibility(View.GONE);
                } else {
                    play.setVisibility(View.VISIBLE);
                    videoView.pause();
                }
            }
        });

        if (fullScreen) {
            play.setVisibility(GONE);
            imgNews.setVisibility(INVISIBLE);
        } else {
            if (videoView.isPlaying())
                play.setVisibility(GONE);
        }

        if (customVideoViewOnListener != null)
            customVideoViewOnListener.onPrepared();
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            videoView.seekTo(progress);
            seekBar.setProgress(progress);
            time(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setLayoutParamsVideo() {
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        imgNews.setLayoutParams(new RelativeLayout.LayoutParams(width, height));

    }

    public void setLayoutParamsVideo(RelativeLayout.LayoutParams layoutParamsVideo) {
        videoView.setLayoutParams(layoutParamsVideo);
    }


    public void setVideoUrl(String url, int currentPosition, boolean isDorado) {
        this.isDorado = isDorado;
        this.url = url;
        videoView.setVideoURI(Uri.parse(url));
        seekBar.setProgress(currentPosition);
        videoView.seekTo(currentPosition);
    }

    private void time(int time) {
        textTime.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) time), TimeUnit.MILLISECONDS.toSeconds((long) time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))));
    }

    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {
            Log.d(TAG, "Probando onEverySecond ");
            if (seekBar != null) {
                seekBar.setProgress(videoView.getCurrentPosition());
                time(videoView.getCurrentPosition());
            }

            if (videoView.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                progressBar.setVisibility(View.GONE);
                return true;
            }
            case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }
            case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                progressBar.setVisibility(View.GONE);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        play.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        // Toast.makeText(getContext(), "No se pudo reproducir el video", Toast.LENGTH_LONG).show();
        error = true;
        return true;
    }

    public int getCurrentPosition() {
        return videoView.getCurrentPosition();
    }

    public void setButtonFullScreen(int drawable, int color, OnClickListener onClickListener) {
        btnFullScreen.setImageResource(drawable);
        btnFullScreen.setColorFilter(color);
        play.setVisibility(GONE);
        btnFullScreen.setOnClickListener(onClickListener);
    }

    public void start() {
        videoView.start();
        play.setVisibility(GONE);
    }

    public void pause() {
        videoView.pause();
        play.setVisibility(VISIBLE);
    }

    public interface CustomVideoViewOnListener {
        void onPrepared();

        void videoIsDorado();
    }

    public void setCustomVideoViewOnListener(CustomVideoViewOnListener customVideoView) {
        this.customVideoViewOnListener = customVideoView;
    }

    public int getVideoHeight() {
        return height;
    }

    public int getVideoWidth() {
        return width;
    }

    public void setImage(Context c, String url) {
        imgNews.setScaleType(ImageView.ScaleType.FIT_XY);
        imgNews.setAdjustViewBounds(true);
        Glide.with(c).load(url).apply(new RequestOptions().override(width, height)).into(imgNews);
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void controllerVisible(int visible) {

        contentController.setVisibility(visible);

    }

    public void setVideoHeight(int height) {
        this.height = height;
    }

    public void setVideoWidth(int width) {
        this.width = width;
    }

    public void setBackgroundColor(int color) {
        contentVideoView.setBackgroundColor(color);
    }


    public void onVideoSizeChanged(int width, int height) {
        updateTextureViewSize(width, height);
        Log.v("EXO", "onVideoSizeChanged() " + width + "x" + height);
    }

    private void updateTextureViewSize(float videoWidth, float videoHeight) {
        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float scaleX = 1.0f;
        float scaleY = 1.0f;

        float viewRatio = viewWidth / viewHeight;
        float videoRatio = videoWidth / videoHeight;
        if (viewRatio > videoRatio) {
            // video is higher than view
            scaleY = videoHeight / videoWidth;
        } else {
            //video is wider than view
            scaleX = videoWidth / videoHeight;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY, viewWidth / 2, viewHeight / 2);


        videoView.setTransform(matrix);
    }

    public void visiblePlay(int visible) {
        play.setVisibility(visible);
    }

}
