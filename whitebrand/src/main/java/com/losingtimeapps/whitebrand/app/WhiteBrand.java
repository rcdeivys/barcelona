package com.losingtimeapps.whitebrand.app;

import android.app.Application;

import com.losingtimeapps.whitebrand.app.di.AppComponent;
import com.losingtimeapps.whitebrand.app.di.AppModule;
import com.losingtimeapps.whitebrand.app.di.DaggerAppComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.YouChoose;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingPresenter;

/**
 * Created by Pedro Gomez on 17/05/2018.
 */

public class WhiteBrand {

    private static Application mInstance;
    private static AppComponent component;

    private WhiteBrand() {
    }

    public static void inittt(Application mIns, ConfiWhiteBrand confiWhiteBrand) {
        mInstance = mIns;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(mInstance, confiWhiteBrand)).build();
    }

    public static Application getmInstance() {
        return mInstance;
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static  YouChoose youChoose() {
        return new YouChoose();
    }
}
