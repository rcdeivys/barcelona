package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by makhnnar on 30/04/18.
 */

public class TiposSuscripcionResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SuscripcionData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SuscripcionData> getData() {
        return data;
    }

    public void setData(List<SuscripcionData> data) {
        this.data = data;
    }
}
