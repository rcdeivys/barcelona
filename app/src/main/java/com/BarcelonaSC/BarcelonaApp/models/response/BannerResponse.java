package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.BannerData;

import java.util.List;

/**
 * Created by Carlos on 06/12/2017.
 */

public class BannerResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<BannerData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }
}