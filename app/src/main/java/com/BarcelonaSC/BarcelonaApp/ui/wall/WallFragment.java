package com.BarcelonaSC.BarcelonaApp.ui.wall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.WallFragmentList;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class WallFragment extends BaseFragment {

    private String type;
    Unbinder unbinder;

    public static WallFragment newInstance() {
        Bundle args = new Bundle();
        WallFragment wallFragment = new WallFragment();
        wallFragment.setArguments(args);

        return wallFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getActivity() instanceof BaseActivity)
                ((BaseActivity) getActivity()).initBanner(BannerView.Seccion.WALL);

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .replace(R.id.cal_container, WallFragmentList.newInstance("", ""), WallFragmentList.TAG)
                .commitAllowingStateLoss();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}