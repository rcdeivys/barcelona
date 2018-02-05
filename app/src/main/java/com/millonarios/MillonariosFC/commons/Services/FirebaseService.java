package com.millonarios.MillonariosFC.commons.Services;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.millonarios.MillonariosFC.app.manager.NotificationManager;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.utils.Notifications;

import java.util.Map;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    //private static RemoteMessageReceiver MessageReceiver;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        Log.d(TAG, "NOTIFICATION FOREGROUND");

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            Log.d(TAG, entry.getKey() + ":" + entry.getValue());
        }


        if (SessionManager.getInstance().getSession() != null) {
               Notifications.sendNormalNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

/*
    public static void setReceiver(RemoteMessageReceiver receiver){
        MessageReceiver = receiver;
    }
    */


}