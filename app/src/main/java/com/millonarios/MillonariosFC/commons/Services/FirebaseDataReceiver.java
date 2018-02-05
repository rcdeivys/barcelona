package com.millonarios.MillonariosFC.commons.Services;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.List;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

/**
 * This is called whenever app receives notification
 * in background/foreground state so you can
 * apply logic for background task, but still Firebase notification
 * will be shown in notification tray
 */
public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    public static String TAG = "FirebaseDataReceiver";

    public void onReceive(Context context, Intent intent) {


        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


        if(isRunning(context)){
            Log.d(TAG, "NOTIFICATION BACKGROUND");
           // Notifications.sendNormalNotification(context,intent.getStringExtra("gcm.notification.title"),intent.getStringExtra("gcm.notification.body"));
        }

    }


    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            Log.d(TAG, "Activity Running "+task.baseActivity.getPackageName());
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }





}
