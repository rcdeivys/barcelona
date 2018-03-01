package com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp;

import com.BarcelonaSC.BarcelonaApp.models.Tournament;
import com.BarcelonaSC.BarcelonaApp.models.Match;

import java.util.List;

/**
 * Created by Erick on 31/10/2017.
 */

public class CalendarPresenter implements CalendarContract.Presenter, CalendarContract.ModelResultListener {

    private CalendarContract.View view;
    private CalendarModel model;
    private List<Tournament> tournamentses;
    private String type;

    public CalendarPresenter(CalendarContract.View view, CalendarModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(CalendarContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetCupSuccess(List<Tournament> tournamentses) {
        for (Tournament tournament : tournamentses) {
            tournament.setMatches(processsMatchData(tournament.getMatches()));
        }
        this.tournamentses = tournamentses;

        if (isViewNull()) return;

        view.setCup(this.tournamentses);
    }

    public List<Match> processsMatchData(List<Match> matches) {

        int newDate = 0;
        String headerInfo;
        boolean initialDate = true;

        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).setNewDate(false);
            for (int j = i + 1; j < matches.size(); j++) {
                if (matches.get(i).getFechaEtapa().equals(matches.get(j).getFechaEtapa())) {
                    matches.get(j).setNewDate(false);
                    matches.get(j).setNewDateHeader(false);
                } else {
                    matches.get(j).setNewDate(false);
                    matches.get(j).setNewDateHeaderTitle(matches.get(j).getFecha());
                    if (initialDate) {
                        initialDate = false;
                        matches.get(j).setNewDate(true);
                    }
                    matches.get(j).setNewDateHeader(true);
                    i = j;
                    break;
                }
            }
        }

        return matches;
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public void getCup() {
        type = "1";
        model.getCups(this);

    }

    public void getCupFb() {
        type = "3";
        model.getCupsFb(this);
    }

    @Override
    public void getMatch() {
        type = "2";
        model.getMatches(this);
    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

}
