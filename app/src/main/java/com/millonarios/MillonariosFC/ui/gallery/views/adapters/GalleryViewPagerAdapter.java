package com.millonarios.MillonariosFC.ui.gallery.views.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.millonarios.MillonariosFC.ui.gallery.views.GalleryFragment;

import java.util.List;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryViewPagerAdapter extends FragmentStatePagerAdapter {

    List<GalleryFragment> galleryFragmentList;

    public GalleryViewPagerAdapter(FragmentManager fm, List<GalleryFragment> galleryFragmentList) {
        super(fm);
        this.galleryFragmentList = galleryFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return galleryFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return galleryFragmentList.size();
    }
}
