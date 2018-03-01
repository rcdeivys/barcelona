package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.UserPhotoData;

/**
 * Created by Carlos on 05/02/2018.
 */

public class UserPhotoResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private UserPhotoData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserPhotoData getData() {
        return data;
    }

    public void setData(UserPhotoData data) {
        this.data = data;
    }

}