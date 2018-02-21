package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.MProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.MSProfileFragment;

/**
 * Created by RYA-Laptop on 19/02/18.
 * <p>
 * Class to manage the monumental's ViewPager adapter
 */

public class MonumentalProfilePagerAdapter extends FragmentStatePagerAdapter {
    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;
    private String monumental;
    private String survey;

    public MProfileFragment monumentalProfileFragment;
    private MSProfileFragment playerSocialMediaFragment;
    public int playerId;

    public MonumentalProfilePagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, String monumental, String survey) {
        super(fm);
        this.monumental = monumental;
        this.survey = survey;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            monumentalProfileFragment = MProfileFragment.newInstance(monumental, survey);
            return monumentalProfileFragment;
        } else {
            playerSocialMediaFragment = MSProfileFragment.newInstance(monumental);
            return playerSocialMediaFragment;
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