package com.losingtimeapps.whitebrand.app.network;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.losingtimeapps.whitebrand.app.ApplicationContext;
import com.losingtimeapps.whitebrand.app.ConfiWhiteBrand;
import com.losingtimeapps.whitebrand.app.di.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carlos-pc on 28/09/2017.
 */

@Module(includes = NetworkModule.class)
public class RetrofitModule {


    @Provides
    @AppScope
    public String provideBaseUrl(ConfiWhiteBrand confiWhiteBrand) {
        return confiWhiteBrand.getServerUrl() + "api/";
    }

    @Provides
    @AppScope
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
     /*   gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());*/
        return gsonBuilder.create();
    }


    @Provides
    @AppScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,
                                    Gson gson,
                                    String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(url)
                .build();
    }

    @Provides
    @AppScope
    @ApplicationContext
    public Retrofit provideRetrofitColombia(OkHttpClient okHttpClient,
                                            Gson gson,
                                            String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("http://fcf.2waysports.com/2waysports/restapi/")
                .build();
    }

}