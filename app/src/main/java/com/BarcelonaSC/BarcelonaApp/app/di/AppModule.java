package com.BarcelonaSC.BarcelonaApp.app.di;

import android.content.Context;
import android.content.res.Resources;


import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 01/10/2017.
 */
@Module
public class AppModule {

    private App app;

    public AppModule(App bizupApp) {
        app = bizupApp;
    }

    @Provides
    @AppScope
    public App provideApp() {
        return app;
    }

    @Provides
    @AppScope
    @ApplicationContext
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    @AppScope
    public Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @AppScope
    public SessionManager provideSessionManager(@ApplicationContext Context context) {
        return new SessionManager(context);
    }

}
