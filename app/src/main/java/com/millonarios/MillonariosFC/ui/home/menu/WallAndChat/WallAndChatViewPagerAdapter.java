package com.millonarios.MillonariosFC.ui.home.menu.WallAndChat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;

import com.millonarios.MillonariosFC.ui.chat.ChatFragment;
import com.millonarios.MillonariosFC.ui.wall.list.WallFragment;

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
            return WallFragment.newInstance();

        } else {
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
