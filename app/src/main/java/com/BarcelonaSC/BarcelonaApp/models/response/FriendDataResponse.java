package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.FriendData;

import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendDataResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<FriendData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FriendData> getData() {
        return data;
    }

    public void setData(List<FriendData> data) {
        this.data = data;
    }

}
