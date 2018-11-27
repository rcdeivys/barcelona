package com.BarcelonaSC.BarcelonaApp.ui.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessagesFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Gianni on 25/07/17.
 * <p>
 * Class to manage the team's ViewPager adapter
 */

public class ChatViewPagerAdapter extends FragmentStatePagerAdapter {

    // Tabs' titles
    private CharSequence Titles[];
    // Number ob tabs
    private int NumbOfTabs;

    public GroupsFragment groupsFragment;
    public MessagesFragment messagesFragment;

    public ChatViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Google Analytics Messages page in Chat
                App.get().registerTrackScreen(Constant.Analytics.CHAT + "." + Constant.ChatTags.Messages);
                return messagesFragment = MessagesFragment.newInstance();

            case 1:
                // Google Analytics Groups page in Chat
                App.get().registerTrackScreen(Constant.Analytics.CHAT + "." + Constant.ChatTags.Groups);
                return groupsFragment = GroupsFragment.getInstance();

            default:
                // Google Analytics Messages page in Chat
                App.get().registerTrackScreen(Constant.Analytics.CHAT + "." + Constant.ChatTags.Messages);
           
                return messagesFragment = MessagesFragment.newInstance();

        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
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
