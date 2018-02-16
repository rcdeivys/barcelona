package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalPoll;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Erick on 14/02/2018.
 */

public class MonumentalPollResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private MonumentalPoll data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MonumentalPoll getData() {
        return data;
    }

    public void setData(MonumentalPoll data) {
        this.data = data;
    }
}
