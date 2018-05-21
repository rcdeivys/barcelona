package com.losingtimeapps.whitebrand.app.network;

import android.content.Context;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.app.ApplicationContext;
import com.losingtimeapps.whitebrand.app.di.AppModule;
import com.losingtimeapps.whitebrand.app.di.AppScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Carlos-pc on 28/09/2017.
 */
@Module(includes = AppModule.class)
public class NetworkModule {


    @Provides
    @AppScope
    public HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @AppScope
    public Cache provideCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB Cahe
    }

    @Provides
    @AppScope
    public File provideCacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @AppScope
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                            Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new ConnectivityInterceptor(WhiteBrand.getmInstance()))
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

}
