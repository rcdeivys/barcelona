package com.BarcelonaSC.BarcelonaApp.ui.home.mvp;

import android.support.v4.app.Fragment;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;

/**
 * Created by Carlos on 01/11/2017.
 */

public class HomeContract {

    public interface Presenter extends MVPContract.Presenter<View> {

    }

    public interface ModelResultListener {

    }

    public interface View {

        Fragment getFragmentByTag(String tag);

        void addFragment(Fragment fragment, String tag);

        void showFragment(Fragment fragment, String tag);

        void setTitle(String title);

        void trackFragment(String fragment);

    }
}
