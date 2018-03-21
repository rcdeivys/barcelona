package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.GalleryFragment;

/**
 * Created by Leonardojpr on 2/5/18.
 */

public class ChatViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int CHAT = 0;
    private static final int IMAGE_VIEW = 1;

    ChatFragment chatFragment;
    GalleryFragment galleryFragment;

    public ChatViewPagerAdapter(FragmentManager fm, ChatFragment chatFragment, GalleryFragment galleryFragment) {
        super(fm);
        this.chatFragment = chatFragment;
        this.galleryFragment = galleryFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CHAT:
                return chatFragment;
            case IMAGE_VIEW:
                return galleryFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
