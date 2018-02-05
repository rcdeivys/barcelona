package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amplex on 1/11/2017.
 */

public class Tournament {

    @SerializedName("copa")
    @Expose
    private String cupName;
    @SerializedName("idcopa")
    @Expose
    private int idCup;
    @SerializedName("partidos")
    @Expose
    private List<Match> matches = null;

    public String getCupName() {
        return cupName;
    }

    public int getIdCup() {
        return idCup;
    }

    public void setIdCup(int idCup) {
        this.idCup = idCup;
    }

    public void setCupName(String cupName) {
        this.cupName = cupName;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> partidos) {
        this.matches = partidos;
    }

}
