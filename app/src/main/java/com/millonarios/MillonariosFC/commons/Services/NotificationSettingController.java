package com.millonarios.MillonariosFC.commons.Services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.millonarios.MillonariosFC.commons.DBController;
import com.millonarios.MillonariosFC.db.NotificationSetting;

import java.util.List;

/**
 * Created by Leonardojpr on 11/27/17.
 */

public class NotificationSettingController {

    private static final String GAMES_REMINDER = "games_reminder";
    private static final String ALIGMENT = "alignment";
    private static final String GAMES_START = "games_start";
    private static final String GOALS = "goals";
    private static final String NEWS_AND_VIDEO = "news_and_videos";

    private static final String TAG = NotificationSettingController.class.getSimpleName();

    public static void initSuscribeTopicAll(Context context) {
        FirebaseMessaging.getInstance().subscribeToTopic(GAMES_REMINDER);
        FirebaseMessaging.getInstance().subscribeToTopic(ALIGMENT);
        FirebaseMessaging.getInstance().subscribeToTopic(GAMES_START);
        FirebaseMessaging.getInstance().subscribeToTopic(GOALS);
        FirebaseMessaging.getInstance().subscribeToTopic(NEWS_AND_VIDEO);

        createSuscribeTopic(context, 1L, GAMES_REMINDER, true);
        createSuscribeTopic(context, 2L, ALIGMENT, true);
        createSuscribeTopic(context, 3L, GAMES_START, true);
        createSuscribeTopic(context, 4L, GOALS, true);
        createSuscribeTopic(context, 5L, NEWS_AND_VIDEO, true);
    }

    public static void saveTopicStatusFirebase(Context context, List<NotificationSetting> list) {
        for (NotificationSetting notificationSetting : list) {
            if (notificationSetting.getStatus()) {
                suscribeTopic(notificationSetting.getTopic());
                updateTopic(context, notificationSetting);
                Log.d(TAG, "topic " + notificationSetting.getTopic() + " status " + notificationSetting.getStatus());
            } else {
                unSuscribeTopic(notificationSetting.getTopic());
                Log.d(TAG, "topic " + notificationSetting.getTopic() + " status " + notificationSetting.getStatus());
                updateTopic(context, notificationSetting);
            }
        }
    }

    private static void updateTopic(Context context, NotificationSetting notificationSetting) {
        DBController.getControler().createNotificationSetting(context, notificationSetting);
    }

    private static void createSuscribeTopic(Context context, Long id, String topic, boolean status) {
        NotificationSetting notificationSetting = new NotificationSetting(id, topic, status);
        DBController.getControler().createNotificationSetting(context, notificationSetting);
    }

    private static void suscribeTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    private static void unSuscribeTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

}
