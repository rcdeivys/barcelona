package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */

public class GameSummonedResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private GameSummonedData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GameSummonedData getData() {
        return data;
    }

    public void setData(GameSummonedData data) {
        this.data = data;
    }
}
