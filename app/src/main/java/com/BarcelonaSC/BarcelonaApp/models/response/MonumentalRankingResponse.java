package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public class MonumentalRankingResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<MonumentalRankingItem> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MonumentalRankingItem> getData() {
        return data;
    }

    public void setData(List<MonumentalRankingItem> data) {
        this.data = data;
    }

}