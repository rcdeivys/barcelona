package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.SingleCalendaryData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 14/11/2017.
 */

public class SingleCalendarResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private SingleCalendaryData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SingleCalendaryData getData() {
        return data;
    }

    public void setData(SingleCalendaryData data) {
        this.data = data;
    }

}