package com.BarcelonaSC.BarcelonaApp.ui.profile.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.profile.fragments.HinchaFragment;
import com.BarcelonaSC.BarcelonaApp.ui.profile.fragments.InfoAccountFragment;

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
                return hinchaFragment;
            default:
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
