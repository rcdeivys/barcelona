package com.millonarios.MillonariosFC.models.response;

import com.millonarios.MillonariosFC.models.CalendarData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Carlos on 14/11/2017.
 */

public class CalendarResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<CalendarData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CalendarData> getData() {
        return data;
    }

    public void setData(ArrayList<CalendarData> data) {
        this.data = data;
    }
}

