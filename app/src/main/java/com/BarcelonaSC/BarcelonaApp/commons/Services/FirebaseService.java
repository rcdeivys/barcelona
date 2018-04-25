package com.BarcelonaSC.BarcelonaApp.commons.Services;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.utils.Notifications;

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
        Log.i(TAG, "--->NOTIFICATION FOREGROUND: " + remoteMessage.getData().toString());

        Log.i(TAG, "--->NOTIFICATION FOREGROUND id: " + remoteMessage.getData().get("id_post"));

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "--->From: " + remoteMessage.getFrom());
            Log.i(TAG, "--->Message data payload: " + remoteMessage.getData());
        }

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            Log.i(TAG, "--->" + entry.getKey() + ":" + entry.getValue());
        }

        if (SessionManager.getInstance().getSession() != null) {
            if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0
                    && remoteMessage.getData().get("seccion") != null
                    && remoteMessage.getData().get("idConversacion") != null
                    && remoteMessage.getData().get("tipoConversacion") != null) {

                Log.i(TAG, "--->sendNormalNotificationChat ");
                String id = "";
                if (remoteMessage.getData().get("idParticipante") != null)
                    id = remoteMessage.getData().get("idParticipante");
                else if (remoteMessage.getData().get("idGrupo") != null)
                    id = remoteMessage.getData().get("idGrupo");

                Notifications.sendNormalNotificationChat(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("seccion"), remoteMessage.getData().get("idConversacion"), remoteMessage.getData().get("tipoConversacion"), id, remoteMessage.getData());

            } else if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0
                    && remoteMessage.getData().get("seccion") != null
                    || remoteMessage.getData().get("id_post") != null) {
                Notifications.sendNormalNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("seccion"), remoteMessage.getData().get("id_post"));
            } else {
                Notifications.sendNormalNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            }
        }
    }

}