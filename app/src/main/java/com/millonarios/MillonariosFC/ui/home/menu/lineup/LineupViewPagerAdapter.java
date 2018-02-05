package com.millonarios.MillonariosFC.ui.home.menu.lineup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.millonarios.MillonariosFC.ui.lineup.idealeleven.IdealElevenFragment;
import com.millonarios.MillonariosFC.ui.lineup.officiallineup.OfficialLineUpFragment;


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

    public LineupViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            officialLineUpFragment = OfficialLineUpFragment.newInstance();
            return officialLineUpFragment;

        } else {
            idealElevenFragment = IdealElevenFragment.newInstance();
            return idealElevenFragment;

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
