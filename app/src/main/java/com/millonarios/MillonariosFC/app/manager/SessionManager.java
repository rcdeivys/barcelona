package com.millonarios.MillonariosFC.app.manager;

import android.content.Context;
import android.util.Log;

import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.BannerData;
import com.millonarios.MillonariosFC.models.UserItem;
import com.millonarios.MillonariosFC.models.response.BannerResponse;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.models.AuthItem;
import com.millonarios.MillonariosFC.utils.PreferenceManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos-pc on 28/09/2017.
 */

public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();

    private UserItem user;
    private AuthItem authItem;
    private PreferenceManager mPrefs;
    private Context mContext;

    private static SessionManager ourInstance;

    public SessionManager(Context context) {
        this.mContext = context;
        mPrefs = new PreferenceManager(context);
    }

    public static SessionManager getInstance() {
        if (ourInstance == null)
            return ourInstance = new SessionManager(App.get().getBaseContext());
        else
            return ourInstance;
    }

    public List<BannerData> getBanners() {
        Gson gson = new Gson();
        BannerResponse bannerResponse = gson.fromJson(mPrefs.getString(Constant.Key.BANNERS, null), BannerResponse.class);
        if (bannerResponse == null) {
            return new ArrayList<>();
        }
        return bannerResponse.getData();
    }

    public void setBanners(BannerResponse banners) {
        Gson gson = new Gson();

        String bannerResponseJson = gson.toJson(banners);
        mPrefs.putString(Constant.Key.BANNERS, bannerResponseJson);
    }


    public UserItem getUser() {
        Gson gson = new Gson();
        UserItem user = gson.fromJson(mPrefs.getString(Constant.Key.USER, null), UserItem.class);
        if (user == null) {
            user = new UserItem();
        }
        return user;
    }

    public AuthItem getSession() {
        Gson gson = new Gson();
        AuthItem authItem = gson.fromJson(mPrefs.getString(Constant.Key.SESSION, null), AuthItem.class);
        return authItem;
    }

    public void setUser(UserItem user) {
        Gson gson = new Gson();

        String userJson = gson.toJson(user);
        mPrefs.putString(Constant.Key.USER, userJson);
        this.user = user;
    }

    public void setSession(AuthItem authItem) {
        Gson gson = new Gson();

        String authJson = gson.toJson(authItem);
        mPrefs.putString(Constant.Key.SESSION, authJson);
        this.authItem = authItem;
    }

    public void setUrlLineUpShare(String url) {
        if (url == null)
            url = "";
        mPrefs.putString(Constant.Key.LINEUP_URL, url.replace("\\", ""));

    }

    public String getUrlLineUpShare() {

        return mPrefs.getString(Constant.Key.LINEUP_URL, "");
    }

    public void setNotificationSwitch(String key, boolean bol) {
        mPrefs.setBoolean(key, bol);
    }

    public boolean getNotificationSwitch(String key) {
        return mPrefs.getBoolean(key, false);
    }

}
