package com.BarcelonaSC.BarcelonaApp.ui.playerdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.PlayerProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial.PlayerSocialMediaFragment;


/**
 * Created by Gianni on 25/07/17.
 * <p>
 * Class to manage the team's ViewPager adapter
 */

public class PlayerPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public PlayerProfileFragment playerProfileFragment;
    private PlayerSocialMediaFragment playerSocialMediaFragment;
    public int playerId;
    public String type;

    public PlayerPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, int playerId, String type) {
        super(fm);
        this.playerId = playerId;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            playerProfileFragment = PlayerProfileFragment.newInstance(playerId, type);
            return playerProfileFragment;
        } else
            playerSocialMediaFragment = PlayerSocialMediaFragment.newInstance(playerId, type);
        return playerSocialMediaFragment;
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
