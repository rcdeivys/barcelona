package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Gianni on 25/07/17.
 *
 * Class to manage the team's ViewPager adapter
 */

public class ApplaudedViewPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public ApplaudedFragment applaudedFragment;
    public ApplaudedFragment applaudedFragment2;


    public ApplaudedViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        // Open "Playoff summoned" fragment
        if(position == 0) {
            // Google analytics Highlights screen per team
            App.get().registerTrackScreen(Constant.Analytics.TEAM + "." + Constant.TeamTags.HighlightsLatest);
            applaudedFragment = ApplaudedFragment.newInstance(Constant.Key.LAST_GAME);
            return applaudedFragment;
        }
        // Open "Game summoned" fragment
        else{
            // Google analytics Highlights All Time screen per team
            App.get().registerTrackScreen(Constant.Analytics.TEAM + "." + Constant.TeamTags.HighlightsAllTime);

            applaudedFragment2 = ApplaudedFragment.newInstance(Constant.Key.ACUMULATED);
            return applaudedFragment2;
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
