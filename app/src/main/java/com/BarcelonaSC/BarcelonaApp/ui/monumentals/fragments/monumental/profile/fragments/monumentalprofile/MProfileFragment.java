package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MProfileFragment extends BaseFragment {

    public static MProfileFragment newInstance(String monumental, String survey) {
        MProfileFragment fragment = new MProfileFragment();
        Bundle args = new Bundle();
        args.putString(Constant.Key.MONUMETAL_ID, monumental);
        args.putString(Constant.Key.SURVEY_ID, survey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monumental_profile, container, false);
        return view;
    }
}