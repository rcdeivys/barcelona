package com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp;

import android.view.View;
import android.widget.Switch;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;

import java.util.List;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

public class SettingsModel implements ISettingsModel {

    public static final String TAG = SettingsModel.class.getSimpleName();

    private SessionManager sessionManager;
    private ISettingsPresenter presenter;


    public SettingsModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // set Presenter
    @Override
    public void setPresenter(ISettingsPresenter presenter){
        this.presenter = presenter;
    }

    // save switch status
    @Override
    public void onCheckedChangedSwitch(View view, boolean checked) {
        sessionManager.setNotificationSwitch(String.valueOf(view.getId()),checked);
    }

    // get switch status
    @Override
    public void getSwitchesStatus(List<Switch> listSwitchs) {
        for (int i = 0; i<listSwitchs.size(); i++){
            //send status to presenter
            presenter.setSwitchStatus(listSwitchs.get(i),sessionManager.getNotificationSwitch(String.valueOf(listSwitchs.get(i).getId())));
        }
    }

}
