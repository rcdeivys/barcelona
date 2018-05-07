package com.BarcelonaSC.BarcelonaApp.ui.home.menu.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Leonardojpr on 3/22/18.
 */

public class MainNewsFragment extends Fragment {

    public static final String TAG = MainNewsFragment.class.getSimpleName();

    private String type;
    Unbinder unbinder;

    public static MainNewsFragment newInstance() {
        Bundle args = new Bundle();
        MainNewsFragment wallFragment = new MainNewsFragment();
        wallFragment.setArguments(args);
        return wallFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .replace(R.id.cal_container, NewsFragment.getInstance(NewsFragment.NEWS_PROFESSIONAL), NewsFragment.TAG)
                .commitAllowingStateLoss();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
