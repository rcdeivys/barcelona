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
    private MultimediaStreamingData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MultimediaStreamingData getData() {
        return data;
    }

    public void setData(MultimediaStreamingData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MultimediaOnLineResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
