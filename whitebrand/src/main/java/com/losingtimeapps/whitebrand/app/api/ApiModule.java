package com.losingtimeapps.whitebrand.app.api;


import com.losingtimeapps.whitebrand.app.di.AppScope;
import com.losingtimeapps.whitebrand.app.network.RetrofitModule;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Carlos-pc on 28/09/2017.
 */
@Module(includes = RetrofitModule.class)
public class ApiModule {

    private static final String TAG = ApiModule.class.getSimpleName();

    @Provides
    @AppScope
    public static YouChooseApi provideYouChooseApi(Retrofit retrofit) {
        return retrofit.create(YouChooseApi.class);
    }


}