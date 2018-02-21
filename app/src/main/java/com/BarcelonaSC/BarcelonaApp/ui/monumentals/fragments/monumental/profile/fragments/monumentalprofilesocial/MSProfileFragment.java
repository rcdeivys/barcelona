package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial;

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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.di.DaggerMSProfileComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.di.MSProfileModule;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp.MSProfileContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp.MSProfilePresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MSProfileFragment extends BaseFragment implements MSProfileContract.View {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    String idMonumental;
    String idEncuesta;

    @Inject
    MSProfilePresenter presenter;

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

    public static MSProfileFragment newInstance(String idMonumental) {
        MSProfileFragment fragment = new MSProfileFragment();
        Bundle args = new Bundle();
        args.putString(Constant.Key.MONUMETAL_ID, idMonumental);
        fragment.setArguments(args);
        return fragment;
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

        idMonumental = getArguments().getString(Constant.Key.MONUMETAL_ID);

        presenter.loadMonumental(idMonumental);

        return view;
    }

    public void initComponent() {
        DaggerMSProfileComponent.builder()
                .appComponent(App.get().component())
                .mSProfileModule(new MSProfileModule(this))
                .build().inject(MSProfileFragment.this);
    }

    private void webViewGoBack() {
        webView.goBack();
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
    public void showMonumental(MonumentalItem itemList) {
        setWebview(itemList.getInstagram());
    }

    @Override
    public void updateVotes() {

    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void navigateToVideoNewsActivity(News news) {

    }

    @Override
    public void navigateToInfografiaActivity(News news) {

    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {

    }

    @Override
    public void navigateToGalleryActivity(int id) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}