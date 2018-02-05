package com.millonarios.MillonariosFC.ui.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.millonarios.MillonariosFC.ui.chat.friends.FriendsFragment;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupsFragment;
import com.millonarios.MillonariosFC.ui.chat.messages.MessagesFragment;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestsFragment;


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

    public FriendsFragment friendsFragment;
    private GroupsFragment groupsFragment;
    private MessagesFragment messagesFragment;
    private RequestsFragment requestsFragment;

    public ChatViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return messagesFragment = MessagesFragment.newInstance();

            case 1:
                return groupsFragment = GroupsFragment.getInstance();

            case 2:
                return friendsFragment = FriendsFragment.newInstance();

            case 3:
                return requestsFragment = RequestsFragment.newInstance();

            default:
                return friendsFragment = FriendsFragment.newInstance();

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
