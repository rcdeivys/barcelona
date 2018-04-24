package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;

import android.os.Parcelable;
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
    private static final int PROFILE = 2;

    ChatFragment chatFragment;
    GalleryFragment galleryFragment;
    public ProfileUserFragment profileUserFragment;

    public ChatViewPagerAdapter(FragmentManager fm, ChatFragment chatFragment, GalleryFragment galleryFragment, ProfileUserFragment profileUserFragment) {
        super(fm);
        this.chatFragment = chatFragment;
        this.galleryFragment = galleryFragment;
        this.profileUserFragment= profileUserFragment;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CHAT:
                return chatFragment;
            case IMAGE_VIEW:
                return galleryFragment;
            case PROFILE:
                return profileUserFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
