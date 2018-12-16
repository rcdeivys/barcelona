package com.BarcelonaSC.BarcelonaApp.commons.Services;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
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

        recieveNotification(remoteMessage.getData(),remoteMessage.getNotification());

//        if (remoteMessage.getData().size() > 0) {
//            Log.i(TAG, "--->From: " + remoteMessage.getFrom());
//            Log.i(TAG, "--->Message data payload: " + remoteMessage.getData());
//        }
//
//        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
//            Log.i(TAG, "--->" + entry.getKey() + ":" + entry.getValue());
//        }
//
//        if (SessionManager.getInstance().getSession() != null) {
//            // Send Received to Google analytics
//            try {
//                App.get().registerTrackEvent(
//                        Constant.Analytics.NOTIFICATION,
//                        Constant.NotificationTags.Received,
//                        remoteMessage.getData().get("section"),
//                        Integer.parseInt(remoteMessage.getData().get("id_post")!=null?remoteMessage.getData().get("id_post"):remoteMessage.getData().get("match_id")!=null?remoteMessage.getData().get("match_id"):"0")
//                );
//            } catch (Exception e) {
//                Log.e("NOTIFICATION","  error --->: "+e.getMessage());
//            }
//

//            if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0
//                    && remoteMessage.getData().get("seccion") != null
//                    && remoteMessage.getData().get("idConversacion") != null
//                    && remoteMessage.getData().get("tipoConversacion") != null) {
//
//                Log.i(TAG, "--->sendNormalNotificationChat ");
//                String id = "";
//                if (remoteMessage.getData().get("idParticipante") != null)
//                    id = remoteMessage.getData().get("idParticipante");
//                else if (remoteMessage.getData().get("idGrupo") != null)
//                    id = remoteMessage.getData().get("idGrupo");
//
//                Notifications.sendNormalNotificationChat(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("seccion"), remoteMessage.getData().get("idConversacion"), remoteMessage.getData().get("tipoConversacion"), id, remoteMessage.getData());
//
//            } else if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0
//                    && remoteMessage.getData().get("seccion") != null
//                    || remoteMessage.getData().get("id_post") != null) {
//                Notifications.sendNormalNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("seccion"), remoteMessage.getData().get("id_post"));
//            } else {
//                Notifications.sendNormalNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//            }
//        }
    }

    private void recieveNotification(Map<String,String> data, RemoteMessage.Notification notification) {

        for (Map.Entry<String, String> entry : data.entrySet()) {
            Log.i(TAG, " === " + entry.getKey() + ":" + entry.getValue());
        }

        if (data != null && SessionManager.getInstance().getSession() != null) {

            try {
                App.get().registerTrackEvent(
                        Constant.Analytics.NOTIFICATION,
                        Constant.NotificationTags.Received,
                        data.get("seccion"),
                        Integer.parseInt(data.get("id_post") != null ? data.get("id_post") : data.get("match_id") != null ? data.get("match_id") : "0")
                );
            } catch (Exception e) {
                App.get().registerTrackEvent(
                        Constant.Analytics.NOTIFICATION,
                        Constant.NotificationTags.Received,
                        data.get("seccion"),
                        Integer.parseInt("-1")
                );
                Log.e("NOTIFICATION", "  error --->: " + e.getMessage());
            }
            try {
                switch (data.get("seccion")) {

                    case Constant.Seccion.CHAT:
                        String id = "";
                        if (data.get("idParticipante") != null)
                            id = data.get("idParticipante");
                        else if (data.get("idGrupo") != null)
                            id = data.get("idGrupo");

                        Notifications.sendNormalNotificationChat(
                                getApplicationContext(),
                                notification.getTitle(),
                                notification.getBody(),
                                data.get("seccion"),
                                data.get("idConversacion"),
                                data.get("tipoConversacion"),
                                id,
                                data
                        );
                        break;

                    default:
                        if (
                                data.get("seccion") != null ||
                                        data.get("id_post") != null ||
                                        data.get("subseccion") != null ||
                                        data.get("punto_referencia") != null
                                ) {
                            Notifications.sendNormalNotification(
                                    getApplicationContext(),
                                    notification.getTitle(),
                                    notification.getBody(),
                                    data.get("seccion"),
                                    data.get("subseccion"),
                                    data.get("id_post"),
                                    data.get("tipo_noticia"),
                                    data.get("punto_referencia")
                            );
                        } else {
                            Notifications.sendNormalNotification(getApplicationContext(), notification.getTitle(), notification.getBody());
                        }
                        break;
                }

            } catch (Exception e) {
                Log.i(TAG,
                        "recieveNotification: === exception: "+
                                e.getLocalizedMessage()+" msg: "+
                                e.getMessage()+" cause: "+
                                e.getCause()
                );
            }
        }
    }
}