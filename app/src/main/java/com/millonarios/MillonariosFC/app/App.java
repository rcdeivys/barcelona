package com.millonarios.MillonariosFC.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.millonarios.MillonariosFC.BuildConfig;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.app.di.AppModule;
import com.millonarios.MillonariosFC.app.di.DaggerAppComponent;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;

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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build(), new Crashlytics());

        FacebookSdk.sdkInitialize(getApplicationContext());
        //FirebaseManager.getInstance().initFirebase();
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