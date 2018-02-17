package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 04/01/2018.
 */

public class ReferredToFragment extends BaseFragment {

    public static final String TAG = ReferredToFragment.class.getSimpleName();
    @BindView(R.id.terms_checkb)
    CheckBox cbTerms;
    @BindView(R.id.terms_btn)
    TextView btnTerms;
    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;
    @BindView(R.id.btn_iniciar)
    Button btnIniciar;
    @BindView(R.id.content_bottom)
    LinearLayout contentBottom;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.img_play)
    ImageView imgPlay;
    @BindView(R.id.content_video_view)
    RelativeLayout contentVideoView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    PreferenceManager preferenceManager;
    Unbinder unbinder;

    boolean isPaused = true;

    public static final String ACCEPT = "accept";

    public static ReferredToFragment instance(boolean first) {
        ReferredToFragment referredToFragment = new ReferredToFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("instrucciones", first);
        referredToFragment.setArguments(bundle);
        return referredToFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referred_to, container, false);
        unbinder = ButterKnife.bind(this, view);
        preferenceManager = new PreferenceManager(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTermsOn();
            }
        });

        if (preferenceManager.getBoolean(ACCEPT, false)) {
            cbTerms.setChecked(true);
        }

        if (!getArguments().getBoolean("instrucciones", false)) {
            if (cbTerms.isChecked()) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ref_container, new ReferredListFragment())
                        .commit();
                preferenceManager.setBoolean(ACCEPT, true);
            }
        } else {
            contentBottom.setVisibility(View.GONE);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getBoolean("instrucciones", false)) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ref_container, new ReferredListFragment())
                            .commit();
                } else {
                    getActivity().finish();
                }
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbTerms.isChecked()) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ref_container, new ReferredListFragment())
                            .commit();
                    preferenceManager.setBoolean(ACCEPT, true);
                } else {
                    Toast.makeText(getActivity(), "Debe haber leído y aceptado los Términos y condiciones.", Toast.LENGTH_LONG).show();
                }
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressBar.setVisibility(View.GONE);
                imgPlay.setVisibility(View.VISIBLE);
                if (!getArguments().getBoolean("instrucciones", false)) {
                    videoView.start();
                    imgPlay.setVisibility(View.GONE);
                    isPaused = false;
                }
                contentVideoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isPaused) {
                            videoView.start();
                            imgPlay.setVisibility(View.GONE);
                            isPaused = false;
                        } else {
                            videoView.pause();
                            imgPlay.setVisibility(View.VISIBLE);
                            isPaused = true;
                        }
                    }
                });
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                imgPlay.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                isPaused = true;
                Toast.makeText(getActivity(), "No se pudo reproducir el video", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                imgPlay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                isPaused = true;
                mediaPlayer.seekTo(1);
            }
        });

        //SessionManager sessionManager = SessionManager.getInstance();
        //Log.d(TAG, "url video: " + ConfigurationManager.getInstance().getConfiguration().getVideoReferidos());
        //Log.d(TAG, "token: " + sessionManager.getSession().getToken());
        if (!ConfigurationManager.getInstance().getConfiguration().getVideoReferidos().equals("")) {
            Uri uri = Uri.parse(ConfigurationManager.getInstance().getConfiguration().getVideoReferidos());
            //Uri uri = Uri.parse("https://s3.amazonaws.com/cmsmillos/videos/video_instrucciones.mp4");
            videoView.setVideoURI(uri);
            videoView.stopPlayback();
        } else {
            Toast.makeText(getContext(), "Apestas", Toast.LENGTH_SHORT).show();
        }

    }

    private void setTermsOn() {
        ReferredTermsFragment referredTermsFragment = new ReferredTermsFragment();
        showDialogFragment(referredTermsFragment);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}