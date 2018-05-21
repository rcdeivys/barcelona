package com.losingtimeapps.whitebrand.app.di;


import com.losingtimeapps.whitebrand.app.api.ApiModule;
import com.losingtimeapps.whitebrand.app.api.YouChooseApi;

import dagger.Component;

/**
 * Created by Carlos on 01/10/2017.
 */

@AppScope
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    YouChooseApi youChooseApi();

}
