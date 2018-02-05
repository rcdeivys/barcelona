package com.millonarios.MillonariosFC.ui.gallery.di;

import com.millonarios.MillonariosFC.app.api.GalleryApi;
import com.millonarios.MillonariosFC.ui.gallery.GalleryListActivity;
import com.millonarios.MillonariosFC.ui.gallery.mvp.GalleryModel;
import com.millonarios.MillonariosFC.ui.gallery.mvp.GalleryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 10/16/17.
 */

@Module
public class GalleryModule {

    private GalleryListActivity activity;

    public GalleryModule(GalleryListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @GalleryScope
    public GalleryListActivity provideActivity() {
        return activity;
    }

    @Provides
    @GalleryScope
    public GalleryModel provideNewsModel(GalleryApi galleryApi) {
        return new GalleryModel(galleryApi);
    }


    @Provides
    @GalleryScope
    public GalleryPresenter providePresenter(GalleryModel model) {
        return new GalleryPresenter(activity, model);
    }


}
