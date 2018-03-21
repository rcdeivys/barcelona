package com.BarcelonaSC.BarcelonaApp.app.manager;

import com.google.gson.Gson;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.NotificationList;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

/**
 * Created by Leonardojpr on 11/26/17.
 */

public class NotificationManager {

    private static final String TAG = NotificationManager.class.getSimpleName();

    private PreferenceManager mPrefs;
    private static NotificationManager ourInstance;

    public static NotificationManager getInstance() {
        if (ourInstance == null)
            return ourInstance = new NotificationManager();
        else
            return ourInstance;
    }

    private NotificationManager() {

        mPrefs = new PreferenceManager(App.get().getApplicationContext());
    }

    public NotificationList getNotification() {
        Gson gson = new Gson();
        NotificationList notificationList = gson.fromJson(mPrefs.getString(Constant.Key.NOTIFICATION, null), NotificationList.class);
        return notificationList;
    }

    public void setConfiguration(NotificationList notificationList) {
        Gson gson = new Gson();
        String userJson = gson.toJson(notificationList);
        mPrefs.putString(Constant.Key.NOTIFICATION, userJson);
    }
}
