package com.millonarios.MillonariosFC.ui.calendar.viewholder;

import com.millonarios.MillonariosFC.models.Match;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Erick on 01/11/2017.
 */

public class LeagueGroup extends ExpandableGroup<Match> {

    public LeagueGroup(String title, List<Match> items) {
        super(title, items);
    }
}
