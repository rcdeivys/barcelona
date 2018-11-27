package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdealElevenRankingEnableData {

    @SerializedName("ranking_activo")
    @Expose
    public boolean ranking_activo;

    public boolean isRanking_activo() {
        return ranking_activo;
    }

    public void setRanking_activo(boolean ranking_activo) {
        this.ranking_activo = ranking_activo;
    }
}