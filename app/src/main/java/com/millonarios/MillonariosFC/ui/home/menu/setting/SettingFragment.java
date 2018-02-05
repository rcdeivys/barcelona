package com.millonarios.MillonariosFC.ui.home.menu.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;

/**
 * Created by Leonardojpr on 11/3/17.
 */

public class SettingFragment extends Fragment {

    public static final String TAG = SettingFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infoacc, container, false);
        return view;

    }
}
