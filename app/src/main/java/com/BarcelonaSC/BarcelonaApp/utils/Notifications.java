package com.BarcelonaSC.BarcelonaApp.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.NewsSingleActivity;
import com.BarcelonaSC.BarcelonaApp.ui.videointro.IntroActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

public class Notifications {

    public static String NotificationsChatTypeGrupal = "grupal";
    public static String NotificationsChatTypeIndividual = "individual";

    public static String currentConversationId = "";

    public static void setCurrentConversationId(String currentConversationId) {
        Notifications.currentConversationId = currentConversationId;
    }

    public static void sendNormalNotification(Context context, String title, String message) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getRequestCode(), notificationBuilder.build());
    }

    private static int getRequestCode() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(900000);
    }

    public static void sendNormalNotification(Context context, String title, String message, String seccion, String id_post) {
        Log.i("--->Notifications", "T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n id_post: " + id_post);
        Intent intent = null;
        if (id_post != null && !id_post.equals("noAplica")) {
            switch (seccion) {
                case Constant.Seccion.MURO: {
                    //TODO GO TO Single_Post_Activity
                    if (Commons.isAppIsInBackground(context)) {
                        intent = new Intent(context, IntroActivity.class);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Seccion.Id_Post, id_post);
                    } else {
                        intent = new Intent(context, SinglePostActivity.class);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Seccion.Id_Post, id_post);
                    }
                    break;
                }
                case Constant.Seccion.NOTICIAS: {
                    //TODO NOTICIAS
                    if (Commons.isAppIsInBackground(context)) {
                        intent = new Intent(context, IntroActivity.class);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Seccion.Id_Post, id_post);
                    } else {
                        intent = new Intent(context, NewsSingleActivity.class);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Seccion.Id_Post, id_post);
                    }
                    break;
                }
                default:
                    intent = new Intent(context, HomeActivity.class);
                    break;
            }
        } else {

            if (seccion != null) {
                Log.i(Notifications.class.getSimpleName(), "T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n id_post: " + id_post);
                if (Commons.isAppIsInBackground(context)) {
                    intent = new Intent(context, IntroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.Key.SECCION, seccion);

                } else {
                    intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.Key.SECCION, seccion);
                }
            }
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getRequestCode(), notificationBuilder.build());


    }

    public static void sendNormalNotificationChat(Context context, String title, String message, String seccion, String idConversacion, String tipoConversacion, String id, Map<String, String> data) {
        Log.i("--->Notifications chat", "T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n idConversacion: " + idConversacion);
        Intent intent = null;

        if (Commons.isAppIsInBackground(context)) {
            currentConversationId = "";
        }

        if (currentConversationId.equals(idConversacion)) {
            return;
        }

        HashMap<String, String> copy = new HashMap<String, String>(data);
        switch (seccion) {
            case Constant.Seccion.CHAT: {
                if (Commons.isAppIsInBackground(context)) {
                    intent = new Intent(context, IntroActivity.class);
                    intent.putExtra(Constant.Key.SECCION, seccion);

                    if (tipoConversacion.equals(NotificationsChatTypeIndividual))
                        intent.putExtra(ChatActivity.TAG_PRIVATE, id);
                    else if (tipoConversacion.equals(NotificationsChatTypeGrupal))
                        intent.putExtra(ChatActivity.TAG_GROUP, id);

                } else {
                    intent = new Intent(context, HomeActivity.class);
                    intent.putExtra(Constant.Key.SECCION, seccion);

                    if (tipoConversacion.equals(NotificationsChatTypeIndividual))
                        intent.putExtra(ChatActivity.TAG_PRIVATE, id);
                    else if (tipoConversacion.equals(NotificationsChatTypeGrupal))
                        intent.putExtra(ChatActivity.TAG_GROUP, id);
                }
                break;
            }
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getRequestCode(), notificationBuilder.build());

    }
}