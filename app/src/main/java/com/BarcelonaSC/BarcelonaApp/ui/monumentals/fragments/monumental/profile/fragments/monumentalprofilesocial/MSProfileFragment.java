package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MSProfileFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    String idMonumental;
    String idEncuesta;


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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_social_media, container, false);

        unbinder = ButterKnife.bind(this, view);

        idMonumental = getArguments().getString(Constant.Key.MONUMETAL_ID);

        //presenter.loadMonumental(idMonumental);

        return view;
    }

}