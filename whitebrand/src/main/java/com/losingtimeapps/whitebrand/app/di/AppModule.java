package com.losingtimeapps.whitebrand.app.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


import com.losingtimeapps.whitebrand.app.ApplicationContext;
import com.losingtimeapps.whitebrand.app.ConfiWhiteBrand;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 01/10/2017.
 */
@Module
public class AppModule {

    private Application app;
    private ConfiWhiteBrand confiWhiteBrand;

    public AppModule(Application application, ConfiWhiteBrand confiWhiteBrand) {
        app = application;
        this.confiWhiteBrand = confiWhiteBrand;
    }

    @Provides
    @AppScope
    public Application provideApp() {
        return app;
    }

    @Provides
    @AppScope
    public ConfiWhiteBrand provideConfiWhiteBrand() {
        return confiWhiteBrand;
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

}
