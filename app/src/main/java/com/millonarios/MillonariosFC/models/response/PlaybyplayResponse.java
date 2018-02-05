package com.millonarios.MillonariosFC.models.response;

import com.millonarios.MillonariosFC.models.PlayByPlay;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 14/11/2017.
 */

public class PlaybyplayResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private PlayByPlay data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PlayByPlay getData() {
        return data;
    }

    public void setData(PlayByPlay data) {
        this.data = data;
    }

}
