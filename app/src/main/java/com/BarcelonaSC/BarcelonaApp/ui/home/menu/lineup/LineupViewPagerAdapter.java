package com.BarcelonaSC.BarcelonaApp.ui.home.menu.lineup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.IdealElevenFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.OfficialLineUpFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.IdealElevenRankingFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;


/**
 * Created by Gianni on 25/07/17.
 * <p>
 * Class to manage the team's ViewPager adapter
 */

public class LineupViewPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public OfficialLineUpFragment officialLineUpFragment;
    public IdealElevenFragment idealElevenFragment;
    public IdealElevenRankingFragment idealElevenRankingFragment;


    public LineupViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                App.get().registerTrackScreen(Constant.Analytics.LINEUP);
                officialLineUpFragment = OfficialLineUpFragment.newInstance();
                return officialLineUpFragment;
            case 1:
                App.get().registerTrackScreen(Constant.Analytics.LINEUP + "." + Constant.LineupTags.Ideal);
                idealElevenFragment = IdealElevenFragment.newInstance();
                return idealElevenFragment;
            case 2:
                App.get().registerTrackScreen(Constant.Analytics.LINEUP + "." + Constant.LineupTags.Ranking);
                idealElevenRankingFragment = new IdealElevenRankingFragment();
                return idealElevenRankingFragment;
            default:
                App.get().registerTrackScreen(Constant.Analytics.LINEUP);
                officialLineUpFragment = OfficialLineUpFragment.newInstance();
                return officialLineUpFragment;
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
