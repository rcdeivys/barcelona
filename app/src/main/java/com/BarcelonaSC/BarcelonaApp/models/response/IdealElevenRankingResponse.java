package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.IdealElevenRankingData;

public class IdealElevenRankingResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private IdealElevenRankingData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IdealElevenRankingData getData() {
        return data;
    }

    public void setData(IdealElevenRankingData data) {
        this.data = data;
    }

}