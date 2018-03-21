package com.BarcelonaSC.BarcelonaApp.models.response;


import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */

public class ApplauseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ApplauseData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApplauseData getData() {
        return data;
    }

    public void setData(ApplauseData data) {
        this.data = data;
    }
}
