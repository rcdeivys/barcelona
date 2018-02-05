package com.millonarios.MillonariosFC.models.response;


import com.millonarios.MillonariosFC.models.NominaItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Carlos on 12/10/2017.
 */

public class PlayoffResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<NominaItem> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NominaItem> getData() {
        return data;
    }

    public void setData(ArrayList<NominaItem> data) {
        this.data = data;
    }
}