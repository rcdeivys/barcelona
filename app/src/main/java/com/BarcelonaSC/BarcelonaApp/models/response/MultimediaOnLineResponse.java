package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deivys on 3/29/2018.
 */

public class MultimediaOnLineResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private MultimediaStreamingData multimediaStreamingData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MultimediaStreamingData getData() {
        return multimediaStreamingData;
    }

    public void setData(MultimediaStreamingData data) {
        this.multimediaStreamingData = data;
    }

}
