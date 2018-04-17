package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 12/10/2017.
 */

public class SetApplauseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("aplauso")
    @Expose
    private int aplauso;
    @SerializedName("error")
    @Expose
    private List<String> error = null;

    public int getAplauso() {
        return aplauso;
    }

    public void setAplauso(int aplauso) {
        this.aplauso = aplauso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

}
