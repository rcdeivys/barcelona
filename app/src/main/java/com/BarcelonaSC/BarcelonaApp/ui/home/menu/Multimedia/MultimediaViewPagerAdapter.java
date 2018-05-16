package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.StreamingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.VideoFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Deivys on 3/29/2018.
 */

public class MultimediaViewPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public VideoFragment videoFragment;
    public StreamingFragment streamingFragment;

    public MultimediaViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        // Open "video" fragment
        if (position == 0) {
            videoFragment = VideoFragment.newInstance(Constant.Key.MULTIMEDIA_VIDEO);
            return videoFragment;
        }
        // Open "on line" fragment
        else {
            //streamingFragment = new StreamingFragment();
            streamingFragment = StreamingFragment.newInstance(Constant.Key.MULTIMEDIA_ONLINE);
            return streamingFragment;
        }
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
