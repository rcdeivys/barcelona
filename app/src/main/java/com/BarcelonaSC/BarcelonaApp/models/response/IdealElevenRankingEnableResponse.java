package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.IdealElevenRankingEnableData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdealElevenRankingEnableResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public IdealElevenRankingEnableData idealElevenRankingEnableData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IdealElevenRankingEnableData getIdealElevenRankingEnableData() {
        return idealElevenRankingEnableData;
    }

    public void setIdealElevenRankingEnableData(IdealElevenRankingEnableData idealElevenRankingEnableData) {
        this.idealElevenRankingEnableData = idealElevenRankingEnableData;
    }
}

