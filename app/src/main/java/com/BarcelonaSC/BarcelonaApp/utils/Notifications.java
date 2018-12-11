package com.BarcelonaSC.BarcelonaApp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.NewsSingleActivity;
import com.BarcelonaSC.BarcelonaApp.ui.videointro.IntroActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import static com.BarcelonaSC.BarcelonaApp.utils.Commons.getString;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

public class Notifications {

    public static String TAG = Notifications.class.getSimpleName();

    public static String NotificationsChatTypeGrupal = "grupal";
    public static String NotificationsChatTypeIndividual = "individual";

    public static String currentConversationId = "";

    public static final String CHANNEL_ID = "my_bsc_channel";// The id of the channel.

    public static void setCurrentConversationId(String currentConversationId) {
        Notifications.currentConversationId = currentConversationId;
    }

    public static void sendNormalNotification(Context context, String title, String message) {
        Log.i("Notifications", "---> T: " + title + "\nmessage" + message);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try{
            getNotification(context,intent,title,message);
        }catch (Exception e){

        }
    }

    private static int getRequestCode() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(900000);
    }

    public static void sendNormalNotification(Context context, String title, String message, String seccion,String subseccion, String id_post,String tipoNoticia,String puntoReferencia) {
        Log.i("Notifications", " ===  T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n subseccion: " + subseccion+ "\n id_post: " + id_post+ "\n tipo_noticia: " + tipoNoticia);
        Log.i("Notifications","  ===  punto referencia = "+puntoReferencia);
        Intent intent = null;
        if (id_post != null && !id_post.equals("noAplica")) {
            switch (seccion.toLowerCase()) {
                case Constant.Seccion.MURO: {
                    //TODO GO TO Single_Post_Activity
                    if (Commons.isAppIsInBackground(context)) {
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                    Log.i(TAG, "sendNormalNotification: === seccion: "+seccion+" id_post: "+id_post);
                    if (Commons.isAppIsInBackground(context)) {
                        Log.i(TAG, "sendNormalNotification: === brackground");
                        if(id_post!=null||id_post!="0"){
                            intent = new Intent(context, NewsSingleActivity.class);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }else {
                            intent = new Intent(context, IntroActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }
                    } else {
                        Log.i(TAG, "sendNormalNotification: === no brackground");
                        if(id_post!=null||id_post!="0"){
                            intent = new Intent(context, NewsSingleActivity.class);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }else {
                            intent = new Intent(context, IntroActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }
                    }
                    break;
                }
                case "noticias": {
                    //TODO NOTICIAS
                    Log.i(TAG, "sendNormalNotification: === seccion: "+seccion+" id_post: "+id_post);
                    if (Commons.isAppIsInBackground(context)) {
                        Log.i(TAG, "sendNormalNotification: === brackground");
                        if(id_post!=null||id_post!="0"){
                            intent = new Intent(context, NewsSingleActivity.class);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }else {
                            intent = new Intent(context, IntroActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }
                    } else {
                        Log.i(TAG, "sendNormalNotification: === no brackground");
                        if(id_post!=null||id_post!="0"){
                            intent = new Intent(context, NewsSingleActivity.class);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }else {
                            intent = new Intent(context, IntroActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, seccion);
                            intent.putExtra(Constant.Seccion.Id_Post, id_post);
                            intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                        }
                    }
                    break;
                }
                default:
                    if(Commons.isAppIsInBackground(context) && subseccion!=null){
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Key.SUB_SECCION, subseccion);
                        intent.putExtra(Constant.Key.PUNTO_REFERENCIA, puntoReferencia);
                        intent.putExtra(Constant.Seccion.Id_Post, id_post);
                    }else{
                        intent = new Intent(context, HomeActivity.class);
                    }
                    break;
            }
        } else {
            if (seccion != null) {
                Log.i(Notifications.class.getSimpleName(), "T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n id_post: " + id_post);
                if (Commons.isAppIsInBackground(context)) {
                    if(seccion.equals(Constant.Seccion.NOTICIAS)){
                        //TODO NOTICIAS
                        Log.i(TAG, "sendNormalNotification: === seccion: "+seccion+" id_post: "+id_post);
                        if (Commons.isAppIsInBackground(context)) {
                            Log.i(TAG, "sendNormalNotification: === brackground");
                            if(id_post!=null||id_post!="0"){
                                intent = new Intent(context, NewsSingleActivity.class);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }else {
                                intent = new Intent(context, IntroActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }
                        } else {
                            Log.i(TAG, "sendNormalNotification: === no brackground");
                            if(id_post!=null||id_post!="0"){
                                intent = new Intent(context, NewsSingleActivity.class);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }else {
                                intent = new Intent(context, IntroActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }
                        }
                    }else if(subseccion!=null){
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Key.SUB_SECCION, subseccion);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                    }else{
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Key.PUNTO_REFERENCIA, puntoReferencia);
                    }
                } else {
                    if(seccion.equals(Constant.Seccion.NOTICIAS)){
                        //TODO NOTICIAS
                        Log.i(TAG, "sendNormalNotification: === seccion: "+seccion+" id_post: "+id_post);
                        if (Commons.isAppIsInBackground(context)) {
                            Log.i(TAG, "sendNormalNotification: === brackground");
                            if(id_post!=null||id_post!="0"){
                                intent = new Intent(context, NewsSingleActivity.class);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }else {
                                intent = new Intent(context, IntroActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }
                        } else {
                            Log.i(TAG, "sendNormalNotification: === no brackground");
                            if(id_post!=null||id_post!="0"){
                                intent = new Intent(context, NewsSingleActivity.class);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }else {
                                intent = new Intent(context, IntroActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(Constant.Key.SECCION, seccion);
                                intent.putExtra(Constant.Seccion.Id_Post, id_post);
                                intent.putExtra(Constant.Seccion.TIPO_NOTICIA, tipoNoticia);
                            }
                        }
                    }else if(subseccion!=null){
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Key.SUB_SECCION, subseccion);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                    }else{
                        intent = new Intent(context, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Key.SECCION, seccion);
                        intent.putExtra(Constant.Key.PUNTO_REFERENCIA, puntoReferencia);
                    }
                }
            }
        }
        try{
            getNotification(context,intent,title,message);
        }catch (Exception e){

        }

    }

    public static void sendNormalNotificationChat(Context context, String title, String message, String seccion, String idConversacion, String tipoConversacion, String id, Map<String, String> data) {
        Log.i("Notifications chat", "---> T: " + title + "\n message: " + message + "\n seccion " + seccion + "\n idConversacion: " + idConversacion);
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
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.Key.SECCION, seccion);

                    if (tipoConversacion.equals(NotificationsChatTypeIndividual))
                        intent.putExtra(ChatActivity.TAG_PRIVATE, id);
                    else if (tipoConversacion.equals(NotificationsChatTypeGrupal))
                        intent.putExtra(ChatActivity.TAG_GROUP, id);

                } else {
                    intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.Key.SECCION, seccion);

                    if (tipoConversacion.equals(NotificationsChatTypeIndividual))
                        intent.putExtra(ChatActivity.TAG_PRIVATE, id);
                    else if (tipoConversacion.equals(NotificationsChatTypeGrupal))
                        intent.putExtra(ChatActivity.TAG_GROUP, id);
                }
                break;
            }
        }
        try{
            getNotification(context,intent,title,message);
        }catch (Exception e){

        }
    }

    private static void getNotification(Context context, Intent intent, String title, String message) {
        intent.putExtra(Constant.Key.NOTIFICATION, "clicked");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setContentIntent(pendingIntent);
        }else{
            notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            notificationManager = context.getSystemService(NotificationManager.class);
        }else{
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int notifyID = 1;
            CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(getRequestCode(), notificationBuilder.build());

    }
}