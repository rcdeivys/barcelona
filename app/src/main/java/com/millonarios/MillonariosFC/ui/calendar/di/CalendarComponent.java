package com.millonarios.MillonariosFC.ui.calendar.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.calendar.CalendarFragment;

import dagger.Component;

/**
 * Created by Erick on 31/10/2017.
 */

@CalendarScope
@Component(dependencies = {AppComponent.class}, modules = {CalendarModule.class})
public interface CalendarComponent {
    void inject(CalendarFragment activity);
}
