package com.BarcelonaSC.BarcelonaApp.ui.futbolbase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.PlayerOffSummonedFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.MainCalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Amplex on 13/11/2017.
 */

public class FutbolBasePagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    private NewsFragment newsFragment;
    private PlayerOffSummonedFragment teamFragment;
    private MainCalendarFragment calendarFragment;

    public FutbolBasePagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            newsFragment = NewsFragment.getInstance(NewsFragment.NEWS_FOOTBALL_BASE);
            return newsFragment;
        } else if (position == 1) {
            teamFragment = PlayerOffSummonedFragment.newInstance(Constant.Key.GAME_FB);
            return teamFragment;
        } else {
            calendarFragment = MainCalendarFragment.newInstance(Constant.Key.CUP_FB);
            return calendarFragment;
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
