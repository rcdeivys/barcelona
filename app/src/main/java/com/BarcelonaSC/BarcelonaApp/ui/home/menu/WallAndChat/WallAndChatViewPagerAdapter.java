package com.BarcelonaSC.BarcelonaApp.ui.home.menu.WallAndChat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.ui.chat.ChatFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.WallFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallAndChatViewPagerAdapter extends FragmentStatePagerAdapter {

    String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit162(),
            ConfigurationManager.getInstance().getConfiguration().getTit163()};

    public WallAndChatViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            // Google Analytics Wall page
            App.get().registerTrackScreen(Constant.Analytics.WALL);
            return WallFragment.newInstance();

        } else {
            App.get().registerTrackScreen(Constant.Analytics.CHAT);
            return ChatFragment.newInstance();

        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
