package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp.PlayerProfileContract;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp.PlayerProfilePresenter;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial.di.DaggerPlayerSocialMediaComponent;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial.di.PlayerSocialMediaModule;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerSocialMediaFragment extends BaseFragment implements PlayerProfileContract.View {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    @Inject
    PlayerProfilePresenter presenter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    public static PlayerSocialMediaFragment newInstance(int playerId, String type) {

        Bundle args = new Bundle();
        args.putInt(Constant.Key.PLAYER_ID, playerId);
        args.putString(Constant.Key.TYPE, type);

        PlayerSocialMediaFragment fragment = new PlayerSocialMediaFragment();

        fragment.setArguments(args);
        return fragment;
    }

    private void webViewGoBack() {
        webView.goBack();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_social_media, container, false);

        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);

        showProgress();
        int playerId = getArguments().getInt(Constant.Key.PLAYER_ID);
        String type = getArguments().getString(Constant.Key.TYPE);
        presenter.onAttach(this);
        if (type.equals(Constant.Key.GAME_FB)) {
            presenter.getPlayerFB(String.valueOf(playerId));
        } else {
            presenter.getPlayer(String.valueOf(playerId));
        }

        return view;
    }

    public void initComponent() {
        DaggerPlayerSocialMediaComponent.builder()
                .appComponent(App.get().component())
                .playerSocialMediaModule(new PlayerSocialMediaModule(this))
                .build().inject(PlayerSocialMediaFragment.this);
    }


    private void setWebview(String playerInstagram) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        String url = "https://www.instagram.com/" + playerInstagram + "/";
        webView.loadUrl(url);
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getImei() {
        return null;
    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToast(String error) {
        hideProgress();
        showToast(error, Toast.LENGTH_LONG);
    }

    @Override
    public void showShareApplause(String id) {

    }

    @Override
    public void setPlayerData(PlayerData player) {
        hideProgress();
        setWebview(player.getInstagram());
    }

    @Override
    public void setPlayerApplause(ApplauseData applauseData) {

    }

    @Override
    public void navigateToVideoNewsActivity(News news, int currentPosition) {

    }

    @Override
    public void navigateToInfografiaActivity(News news) {

    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {

    }

    @Override
    public void navigateToGalleryActivity(News news) {

    }


    @Override
    public void showDialogDorado() {

    }
}
