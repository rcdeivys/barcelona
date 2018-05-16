package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.MapData;

import java.util.List;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public class MapResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<MapData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MapData> getData() {
        return data;
    }

    public void setData(List<MapData> data) {
        this.data = data;
    }

}