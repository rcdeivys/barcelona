package com.BarcelonaSC.BarcelonaApp.ui.profile.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.profile.fragments.HinchaFragment;
import com.BarcelonaSC.BarcelonaApp.ui.profile.fragments.InfoAccountFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by leonardojpr on 11/10/17.
 */

public class ProfileViewPager   extends FragmentStatePagerAdapter {

    private final int HINCHAFRAGMENT = 0;
    private final int INFOACCFRAGMENT = 1;
    private Context context;

    HinchaFragment hinchaFragment;
    InfoAccountFragment infoAccountFragment;

    String[] titles;

    public ProfileViewPager(FragmentManager fm, Context context, HinchaFragment hinchaFragment, InfoAccountFragment infoAccountFragment, String[] titles) {
        super(fm);
        this.context = context.getApplicationContext();
        this.hinchaFragment = hinchaFragment;
        this.infoAccountFragment = infoAccountFragment;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Google Analytics tag for Profile screen
                App.get().registerTrackScreen(Constant.Analytics.PROFILE);

                return hinchaFragment;
            default:
                // Google Analytics tag for changing profile info screen
                App.get().registerTrackScreen(Constant.Analytics.PROFILE + "." + Constant.ProfileTags.Info);

                return infoAccountFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case HINCHAFRAGMENT:
                return titles[position];
            case INFOACCFRAGMENT:
                return titles[position];
            default:
                return titles[position];
        }

    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
