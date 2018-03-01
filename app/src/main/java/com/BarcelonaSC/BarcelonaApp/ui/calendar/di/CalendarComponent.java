package com.BarcelonaSC.BarcelonaApp.ui.calendar.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.CalendarFragment;

import dagger.Component;

/**
 * Created by Erick on 31/10/2017.
 */

@CalendarScope
@Component(dependencies = {AppComponent.class}, modules = {CalendarModule.class})
public interface CalendarComponent {
    void inject(CalendarFragment activity);
}
