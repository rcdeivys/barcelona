package com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.Match;
import com.BarcelonaSC.BarcelonaApp.models.Tournament;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Erick on 31/10/2017.
 */

public class CalendarPresenter implements CalendarContract.Presenter, CalendarContract.ModelResultListener {

    private String TAG = CalendarPresenter.class.getSimpleName();
    private CalendarContract.View view;
    private CalendarModel model;
    private List<Tournament> tournaments;
    private String type;

    //ORDENAMIENTO
//    List<Match> allMatches = new ArrayList<>();
    private AuxFinderMatch aux = new AuxFinderMatch();
    SimpleDateFormat simpleDateFormat;

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

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onGetCupSuccess(List<Tournament> tournaments) {
//        for (Tournament tournament : tournamentses) {
//            tournament.setMatches(processMatchData(tournament.getMatches()));
//        }
//        this.tournamentses = tournamentses;
//        if (isViewNull()) return;
//        view.setCup(this.tournamentses);

        Log.e(TAG, "---> Llega: " + tournaments.size());
        int gruopNumber = 0;
//        try {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = simpleDateFormat.format(new Date());
        Date today = null;
        try {
            today = simpleDateFormat.parse(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        aux.setTodayDate(today.getTime());

        Log.e(TAG, "---> NO LLEGA AQUI");

        for (Tournament tournament : tournaments) {
            Log.e(TAG, "---> AQUI 1 tournament.getMatches():  " + tournament.getMatches());
            tournament.setMatches(processMatchData(tournament.getMatches(), gruopNumber));
            gruopNumber++;
            Log.e(TAG, "---> AQUI tournament: " + tournament.toString());
        }
        Log.e(TAG, "---> NO LLEGA AQUI");
        this.tournaments = tournaments;
        Log.e(TAG, "---> NO LLEGA AQUI this.tournaments.size(): " + this.tournaments.size());


        if (isViewNull()) return;
        view.setCup(this.tournaments);

        PreferenceManager.getInstance().putInt(Constant.Key.CUR_GROUP_POS, aux.getPosGroup());
        PreferenceManager.getInstance().putInt(Constant.Key.CUR_ITEM_POS, aux.getPosItem());

//        } catch (Exception e) {
//            Log.e(TAG, "---> CATCH: " + e.toString());
//        }

    }

//    public List<Match> processMatchData(List<Match> matches) {
//
//        int newDate = 0;
//        String headerInfo;
//        boolean initialDate = true;
//
//        for (int i = 0; i < matches.size(); i++) {
//            matches.get(i).setNewDate(false);
//            for (int j = i + 1; j < matches.size(); j++) {
//                if (matches.get(i).getFechaEtapa().equals(matches.get(j).getFechaEtapa())) {
//                    matches.get(j).setNewDate(false);
//                    matches.get(j).setNewDateHeader(false);
//                } else {
//                    matches.get(j).setNewDate(false);
//                    matches.get(j).setNewDateHeaderTitle(matches.get(j).getFecha());
//                    if (initialDate) {
//                        initialDate = false;
//                        matches.get(j).setNewDate(true);
//                    }
//                    matches.get(j).setNewDateHeader(true);
//                    i = j;
//                    break;
//                }
//            }
//        }
//
//        return matches;
//    }

    public List<Match> processMatchData(List<Match> matches, int gruopNumber) {
        Log.e(TAG, "---> processMatchData");
        boolean initialDate = true;
        foundMostRecent(matches, gruopNumber);

        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).setNewDate(false);
            for (int j = i + 1; j < matches.size(); j++) {
                if (matches.get(i).getFechaEtapa() != null) {
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
        }

        Log.e(TAG, "---> processMatchData return matches " + matches.toString());
        return matches;
    }

    private void foundMostRecent(List<Match> matches, int gruopNumber) {
        Log.e(TAG, "---> foundMostRecent");

        int i = 0;
        if (matches != null && matches.size() > 0) {

            while (matches.get(i).getFechaEtapa() == null) {
                i++;
            }

            try {
                Object match = matches.get(i);
                Match partido = (Match) match;
                String[] parts = partido.getFecha().split(" ");

                Date date = simpleDateFormat.parse(parts[0]);
                long mills = date.getTime();
                if (aux.getDateDistancePast() == 0) {
                    aux.setDateDistancePast(Math.abs(aux.getTodayDate() - mills));
                }
                if (aux.getDateDistanceFuture() == 0) {
                    aux.setDateDistanceFuture(Math.abs(aux.getTodayDate() - mills));
                }


                while (i < matches.size()) {
                    Log.e(TAG, "---> matches.size() " + matches.size() + " i: " + i);

                    match = matches.get(i);
//                Log.e(TAG, "---> " + "foundMostRecent: match.toString(): (" + i + "): " + match.toString());
                    try {
                        partido = (Match) match;
                        parts = partido.getFecha().split(" ");

                        date = simpleDateFormat.parse(parts[0]);
                        mills = date.getTime();
                        long auxDistance = Math.abs(aux.getTodayDate() - mills);
                        if (mills >= aux.getTodayDate()) {//si encontramos una fecha futura
                            //este es el mas cercano por el futuro
                            aux.setWasFound(true);
                            if (aux.getDateDistanceFuture() >= auxDistance) {
                                Log.e(TAG, "---> " + "foundMostRecent Encontro por el futuro i---> " + i);
                                aux.setDateDistanceFuture(auxDistance);
                                aux.setPosItem(i);
                                aux.setPosGroup(gruopNumber);
                                aux.setAllItemUntilNow(aux.getAllItemUntilNow() + i);
                            }
                        } else if (!aux.isWasFound()) {
                            //si aun no hemos encontrado una fecha futura. Preferencia por las futuras
                            //este es el mas cercano por el pasado
                            if (aux.getDateDistancePast() > auxDistance) {
                                Log.e(TAG, "---> " + "foundMostRecent Encontro por el pasado i---> " + i);
                                aux.setDateDistancePast(auxDistance);
                                aux.setPosItem(i);
                                aux.setPosGroup(gruopNumber);
                                aux.setAllItemUntilNow(aux.getAllItemUntilNow() + i);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "---> foundMostRecent catch: " + e.toString());
                    }
                    i++;
                }
                Log.e(TAG, "---> foundMostRecent fin while  aux.toString(): " + aux.toString());
            } catch (Exception e) {
                Log.e(TAG, "---> " + "foundMostRecent No se guardo la pos: " + e.getMessage());
            }
        }
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
