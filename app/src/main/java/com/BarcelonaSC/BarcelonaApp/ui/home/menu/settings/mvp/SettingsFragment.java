package com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.di.SettingsModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.setting.SettingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.di.DaggerSettingsComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


/**
 * Created by JulianOtalora on 17/11/2017.
 */

public class SettingsFragment extends Fragment implements ISettingsView {

    public static final String TAG = SettingFragment.class.getSimpleName();

    // BindViews
    @BindViews({R.id.switchnotifications, R.id.switchlineup, R.id.switchmatch, R.id.switchgoals, R.id.switchnews})
    List<Switch> listSwitchs;

    // Inject Presenter
    @Inject
    SettingsPresenter presenter;


    // Create View
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    // OnChecked Buttons
    @OnCheckedChanged({R.id.switchnotifications, R.id.switchlineup, R.id.switchmatch, R.id.switchgoals, R.id.switchnews})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        presenter.onCheckedChangedSwitch(button,checked);
    }

    // Int ButterKnife, Dagger Component And Switches Status
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initComponent();
        presenter.getSwitchesStatus(listSwitchs);
    }

    // Int Dagger
    public void initComponent() {
        DaggerSettingsComponent.builder()
                .appComponent(App.get().component())
                .settingsModule(new SettingsModule(this))
                .build().inject(SettingsFragment.this);
    }

    // Set Switch Status
    @Override
    public void setSwitchStatus(View view, boolean checked) {
        Switch current = (Switch)view;
        current.setChecked(checked);
    }
}
