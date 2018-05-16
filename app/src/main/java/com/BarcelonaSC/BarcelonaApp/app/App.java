package com.BarcelonaSC.BarcelonaApp.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.BuildConfig;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.app.di.AppModule;
import com.BarcelonaSC.BarcelonaApp.app.di.DaggerAppComponent;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Carlos on 25/09/2017.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    public static App mInstance;

    private static App INSTANCE;
    private static StatusApp status;
    private AppComponent component;
    private Tracker mTracker;
    private String state = "close";

    public static Context getAppContext() {
        return INSTANCE.getApplicationContext();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static App get() {
        return INSTANCE;
    }

    public AppComponent component() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Fresco.initialize(this);
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build(), new Crashlytics());

        FacebookSdk.sdkInitialize(getApplicationContext());
        if (SessionManager.getInstance().getSession() != null)
            FirebaseManager.getInstance().initFirebase();
        AppEventsLogger.activateApp(this);
        status = StatusApp.FOREGROUND;
        try {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.font_roboto_regular))
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        } catch (Exception e) {
            // Calligraphy fails on some devices (4.x)
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.millonarios.MillonariosFC",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();

        //FirebaseManager firebaseManager = new FirebaseManager();
        //firebaseManager.initFirebase();
        deleteCache(getApplicationContext());
    }

    public static void deleteCache(Context context) {
        try {


            File dirExt = context.getExternalCacheDir();
            File dir = context.getCacheDir();
            long sizeMaxMb = 200;
            long sizeInMbFile = dir.length();

            long size = 0;
            long sizeExt = 0;
            File[] filesDir = dir.listFiles();
            for (File f : filesDir) {
                size = size + f.length();
            }

            File[] filesExt = dir.listFiles();
            for (File f : filesExt) {
                sizeExt = size + f.length();
            }
            if (size + sizeExt / (1024) > sizeMaxMb * 1024 * 1024)
                deleteDir(dir);
        } catch (Exception e) {
        }
    }

    private static String filesize_in_megaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public synchronized void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN)) {
            status = StatusApp.BACKGROUND;
        }
        super.onTrimMemory(level);
    }

    public synchronized static void onResumeActivity(Context context, boolean splash) {
        if (status == StatusApp.BACKGROUND) {
        }
        status = StatusApp.FOREGROUND;
    }


    private enum StatusApp {
        BACKGROUND,
        FOREGROUND
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.analytics);
        }
        return mTracker;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public void registerTrackScreen(String trackScreen) {
        mTracker = getDefaultTracker();
        mTracker.setScreenName(trackScreen);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void changeState(String state) {
        this.state = state;
    }

}