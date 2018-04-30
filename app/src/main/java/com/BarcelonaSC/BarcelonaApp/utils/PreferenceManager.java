package com.BarcelonaSC.BarcelonaApp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.BarcelonaSC.BarcelonaApp.app.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Leonardojpr on 7/17/17.
 */

public class PreferenceManager {

    private String TAG = PreferenceManager.class.getSimpleName();
    private SharedPreferences mPref = null;
    private Context context;

    private static PreferenceManager ourInstance;

    public static PreferenceManager getInstance() {
        if (ourInstance == null)
            return ourInstance = new PreferenceManager(App.getAppContext());
        else
            return ourInstance;
    }

    /**
     * @param context
     */
    public PreferenceManager(Context context) {
        this.context = context;
        TAG = PreferenceManager.class.getSimpleName();
        mPref = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public PreferenceManager() {
        this.context = App.getAppContext();
        TAG = PreferenceManager.class.getSimpleName();
        mPref = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    /**
     * @param act
     * @param context
     * @param name
     */
    private PreferenceManager(Activity act, Context context, String name) {
        this.context = context;
        TAG = PreferenceManager.class.getSimpleName();
        mPref = act.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * @param key
     * @param value
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean putString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public boolean putStringSet(String key, List<String> values) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putStringSet(key, new HashSet<>(values));
        return editor.commit();
    }

    /**
     * @param key
     * @return
     */
    public List<String> getStringSet(String key) {
        List<String> list = new ArrayList<>();
        Set<String> vals = mPref.getStringSet(key, null);
        if (vals != null)
            list.addAll(vals);
        return list;
    }

    /**
     * @param key
     * @param default_value
     * @return
     */
    public String getString(String key, String default_value) {
        return mPref.getString(key, default_value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean putInt(String key, int value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * @param key
     * @param default_value
     * @return
     */
    public int getInt(String key, int default_value) {
        try {
            return Integer.parseInt(mPref.getString(key, default_value + ""));
        } catch (Exception e) {
            return mPref.getInt(key, default_value);
        }
    }

    /**
     * @param key
     * @param value
     */
    public void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    /**
     * @param key
     * @param value
     */
    public void setDate(String key, Date value) {
        long time = value.getTime();
        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(key, time);
        editor.commit();
    }

    /**
     * @param key
     * @return
     */
    public Date getDate(String key) {
        try {
            long value = mPref.getLong(key, -1);
            if (value != -1) {
                return new Date(value);
            } else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Context getContext() {
        return context;
    }
}
