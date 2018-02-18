package com.BarcelonaSC.BarcelonaApp.ui.monumentals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.adapters.MonumentalPagerAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.MonumentalFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.MonumentalNewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.MonumentalRankingFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalMainFragment extends BaseFragment {

    public static final String TAG = MonumentalMainFragment.class.getSimpleName();

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;

    MonumentalNewsFragment monumentalNewsFragment;
    MonumentalFragment monumentalFragment;
    MonumentalRankingFragment monumentalRankingFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monumentals, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initViewPager();
    }

    private void initViewPager() {
        monumentalNewsFragment = new MonumentalNewsFragment();
        monumentalFragment = new MonumentalFragment();
        monumentalRankingFragment = new MonumentalRankingFragment();

        pager.setAdapter(new MonumentalPagerAdapter(getChildFragmentManager(), getActivity(), monumentalNewsFragment, monumentalFragment, monumentalRankingFragment));
        pager.setOffscreenPageLimit(0);
        tabs.setupWithViewPager(pager);
    }

}