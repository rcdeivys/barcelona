package com.millonarios.MillonariosFC.ui.gallery.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.gallery.GalleryListActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 10/16/17.
 */

@GalleryScope
@Component(dependencies = {AppComponent.class}, modules = {GalleryModule.class})
public interface GalleryComponent {
    void inject(GalleryListActivity activity);
}
