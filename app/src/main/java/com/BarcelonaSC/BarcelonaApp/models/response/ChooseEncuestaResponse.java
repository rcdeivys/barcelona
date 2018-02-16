package com.BarcelonaSC.BarcelonaApp.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.models.EncuestaData;

/**
 * Created by Carlos on 12/01/2018.
 */

public class ChooseEncuestaResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private EncuestaData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EncuestaData getData() {
        return data;
    }

    public void setData(EncuestaData data) {
        this.data = data;
    }

}