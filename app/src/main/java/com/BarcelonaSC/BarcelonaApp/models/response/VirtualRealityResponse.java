package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gianni on 26/07/17.
 *
 * Class to manage videos' response
 */

public class VirtualRealityResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<VideoReality> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<VideoReality> getData() {
        return data;
    }

    public void setData(ArrayList<VideoReality> data) {
        this.data = data;
    }
}
