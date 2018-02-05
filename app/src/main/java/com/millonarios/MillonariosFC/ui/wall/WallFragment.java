package com.millonarios.MillonariosFC.ui.wall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.commons.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Carlos on 22/01/2018.
 */

public class WallFragment  extends BaseFragment{

    public static WallFragment newInstance() {
        Bundle args = new Bundle();
        WallFragment fragment = new WallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);
        return view;

    }


}
