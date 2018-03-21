package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.MossApplaudedFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.PlayerOffSummonedFragment;

/**
 * Created by Gianni on 25/07/17.
 *
 * Class to manage the team's ViewPager adapter
 */

public class TeamViewPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public PlayerOffSummonedFragment playoffSummonedFragment;
    public PlayerOffSummonedFragment gameSummonedFragment;
    public MossApplaudedFragment mostApplaudedPlayerFragment;

    public TeamViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        // Open "Playoff summoned" fragment
        if(position == 0) {
            playoffSummonedFragment = PlayerOffSummonedFragment.newInstance("playerOff");
            return playoffSummonedFragment;
        }
        // Open "Game summoned" fragment
        else if (position == 1){
            gameSummonedFragment = PlayerOffSummonedFragment.newInstance("gameSupponned");
            return gameSummonedFragment;
        }
        // Open "Most applauded player" fragment
       else {
            mostApplaudedPlayerFragment =  MossApplaudedFragment.newInstance();
            return mostApplaudedPlayerFragment;
        }


    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }*/

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
