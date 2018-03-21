package com.BarcelonaSC.BarcelonaApp.ui.gallery.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.GalleryListActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 10/16/17.
 */

@GalleryScope
@Component(dependencies = {AppComponent.class}, modules = {GalleryModule.class})
public interface GalleryComponent {
    void inject(GalleryListActivity activity);
}
