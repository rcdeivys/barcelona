package com.millonarios.MillonariosFC.ui.home.menu.settings.mvp;

import android.view.View;
import android.widget.Switch;

import java.util.List;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

public interface ISettingsPresenter {


    void onCheckedChangedSwitch(View view, boolean checked);
    void getSwitchesStatus(List<Switch> listSwitchs);
    void setSwitchStatus(View view, boolean checked);
}
