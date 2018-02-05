package com.millonarios.MillonariosFC.ui.calendar.di;

import com.millonarios.MillonariosFC.ui.calendar.mvp.CalendarPresenter;
import com.millonarios.MillonariosFC.app.api.TournamentApi;
import com.millonarios.MillonariosFC.ui.calendar.CalendarFragment;
import com.millonarios.MillonariosFC.ui.calendar.mvp.CalendarModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Erick on 31/10/2017.
 */

@Module
public class CalendarModule {

    private CalendarFragment fragment;

    public CalendarModule(CalendarFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @CalendarScope
    public CalendarModel provideModel(TournamentApi tournamentApi) {
        return new CalendarModel(tournamentApi);
    }

    @Provides
    @CalendarScope
    public CalendarPresenter providePresenter(CalendarModel model) {
        return new CalendarPresenter(fragment, model);
    }

}