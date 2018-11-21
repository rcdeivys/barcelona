package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdealElevenRankingData {

    @SerializedName("alineacion_oficial")
    @Expose
    private List<AlineacionOficialData> alineacionOficial = null;
    @SerializedName("once_ideal")
    @Expose
    private List<OnceIdealData> onceIdealData = null;
    @SerializedName("ranking")
    @Expose
    private List<Ranking> ranking = null;

    public List<AlineacionOficialData> getAlineacionOficial() {
        return alineacionOficial;
    }

    public void setAlineacionOficial(List<AlineacionOficialData> alineacionOficial) {
        this.alineacionOficial = alineacionOficial;
    }

    public List<OnceIdealData> getOnceIdealData() {
        return onceIdealData;
    }

    public void setOnceIdealData(List<OnceIdealData> onceIdealData) {
        this.onceIdealData = onceIdealData;
    }

    public List<Ranking> getRanking() {
        return ranking;
    }

    public void setRanking(List<Ranking> ranking) {
        this.ranking = ranking;
    }

}