package com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp;

import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.List;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

public class SettingsPresenter implements ISettingsPresenter {
    public static final String TAG = SettingsPresenter.class.getSimpleName();

    // View And Model
    private ISettingsView view;
    private ISettingsModel model;


    // Presenter Constructor
    public SettingsPresenter(ISettingsView settingsView, ISettingsModel model) {
        this.view = settingsView;
        this.model = model;
        // set presenter in model
        model.setPresenter(this);
    }

    //Notify Model Switch Status Changed
    @Override
    public void onCheckedChangedSwitch(View view, boolean checked) {
    model.onCheckedChangedSwitch(view,checked);
    Log.d(TAG,view.getId()+" : "+checked);
    }

    // get Switches Status
    @Override
    public void getSwitchesStatus(List<Switch> listSwitchs) {
    model.getSwitchesStatus(listSwitchs);
    }

    // set Single Switch Status
    @Override
    public void setSwitchStatus(View view, boolean checked) {
        this.view.setSwitchStatus(view,checked);
    }


}
