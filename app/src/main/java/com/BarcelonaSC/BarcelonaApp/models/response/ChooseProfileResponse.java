package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.ChooseProfileData;

/**
 * Created by Carlos on 12/01/2018.
 */

public class ChooseProfileResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ChooseProfileData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChooseProfileData getData() {
        return data;
    }

    public void setData(ChooseProfileData data) {
        this.data = data;
    }

}