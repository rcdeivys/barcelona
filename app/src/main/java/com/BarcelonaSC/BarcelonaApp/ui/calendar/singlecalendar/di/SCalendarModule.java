package com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.di;

import com.BarcelonaSC.BarcelonaApp.app.api.SCalendarApi;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.SingleCalendarListFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp.SCalendarModel;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp.SCalendarPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Erick on 01/11/2017.
 */

@Module
public class SCalendarModule {

    private SingleCalendarListFragment fragment;

    public SCalendarModule(SingleCalendarListFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @SCalendarScope
    public SCalendarModel provideModel(SCalendarApi sCalendarApi) {
        return new SCalendarModel(sCalendarApi);
    }

    @Provides
    @SCalendarScope
    public SCalendarPresenter providePresenter(SCalendarModel model) {
        return new SCalendarPresenter(fragment, model);
    }

}
