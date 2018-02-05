package com.millonarios.MillonariosFC.app.network;

import android.content.res.Resources;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.ApplicationContext;
import com.millonarios.MillonariosFC.app.di.AppScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public String provideBaseUrl(Resources resources) {
        return resources.getString(R.string.url_api) + "api/";
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