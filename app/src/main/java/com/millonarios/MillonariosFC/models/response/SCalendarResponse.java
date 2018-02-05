package com.millonarios.MillonariosFC.models.response;

import com.millonarios.MillonariosFC.models.SCalendarData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Erick on 01/11/2017.
 */

public class SCalendarResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private SCalendarData data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SCalendarData getData() {
        return data;
    }

    public void setData(SCalendarData data) {
        this.data = data;
    }

}
