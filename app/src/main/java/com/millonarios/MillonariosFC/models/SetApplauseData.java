package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */


public class SetApplauseData {
    @SerializedName("yaaplaudio")
    @Expose
    private String yaaplaudio;

    public String getYaaplaudio() {
        return yaaplaudio;
    }

    public void setYaaplaudio(String yaaplaudio) {
        this.yaaplaudio = yaaplaudio;
    }
}
