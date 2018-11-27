package com.BarcelonaSC.BarcelonaApp.ui.monumentals.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.MonumentalFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.MonumentalNewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.MonumentalRankingFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabsNumber = 3;
    private Context context;

    private MonumentalNewsFragment monumentalNewsFragment;
    private MonumentalFragment monumentalFragment;
    private MonumentalRankingFragment monumentalRankingFragment;

    public MonumentalPagerAdapter(FragmentManager fm, Context context, MonumentalNewsFragment monumentalNewsFragment, MonumentalFragment monumentalFragment, MonumentalRankingFragment monumentalRankingFragment) {
        super(fm);
        this.context = context.getApplicationContext();
        this.monumentalNewsFragment = monumentalNewsFragment;
        this.monumentalFragment = monumentalFragment;
        this.monumentalRankingFragment = monumentalRankingFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Google Analytics for GLORIAS Screen
                App.get().registerTrackScreen(Constant.Analytics.GLORIAS + "." + Constant.GloriasTags.VideoFeed);

                return monumentalNewsFragment;
            case 1:
                // Google Analytics for GLORIAS Screen
                App.get().registerTrackScreen(Constant.Analytics.GLORIAS + "." + Constant.GloriasTags.Vote);

                return monumentalFragment;
            case 2:
                // Google Analytics for GLORIAS Screen
                App.get().registerTrackScreen(Constant.Analytics.GLORIAS + "." + Constant.GloriasTags.Ranking);

                return monumentalRankingFragment;
            default:
                // Google Analytics for GLORIAS Screen
                App.get().registerTrackScreen(Constant.Analytics.GLORIAS + "." + Constant.GloriasTags.VideoFeed);

                return monumentalNewsFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Galería";
            case 1:
                return "Votación";
            case 2:
                return "Ranking";
            default:
                return "Galería";
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}