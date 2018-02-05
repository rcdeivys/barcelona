package com.millonarios.MillonariosFC.app.manager;

import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.ConfigurationItem;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.utils.PreferenceManager;
import com.google.gson.Gson;

/**
 * Created by Leonardojpr on 11/3/17.
 */

public class ConfigurationManager {
    private static final String TAG = SessionManager.class.getSimpleName();

    private PreferenceManager mPrefs;
    private static ConfigurationManager ourInstance;

    public static ConfigurationManager getInstance() {
        if (ourInstance == null)
            return ourInstance = new ConfigurationManager();
        else
            return ourInstance;
    }

    private ConfigurationManager() {

        mPrefs = new PreferenceManager(App.get().getApplicationContext());
    }

    public ConfigurationItem getConfiguration() {
        Gson gson = new Gson();
        ConfigurationItem configurationItem = gson.fromJson(mPrefs.getString(Constant.Key.CONFIGURATION, null), ConfigurationItem.class);
        if (configurationItem == null) {
            configurationItem = new ConfigurationItem();
        }
        return configurationItem;
    }

    public void setConfiguration(ConfigurationItem configuration) {
        Gson gson = new Gson();

        String userJson = gson.toJson(configuration);
        mPrefs.putString(Constant.Key.CONFIGURATION, userJson);
    }

}
