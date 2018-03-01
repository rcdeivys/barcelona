package com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.SingleCalendarListFragment;

import dagger.Component;

/**
 * Created by Erick on 01/11/2017.
 */

@SCalendarScope
@Component(dependencies = {AppComponent.class}, modules = {SCalendarModule.class})
public interface SCalendarComponent {
    void inject(SingleCalendarListFragment activity);
}
