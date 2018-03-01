package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 15/11/2017.
 */

public class MyIdealElevenResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private List<String> error = null;
    @SerializedName("data")
    @Expose
    private SendMyIdealElevenData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SendMyIdealElevenData getData() {
        return data;
    }

    public void setData(SendMyIdealElevenData data) {
        this.data = data;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}

