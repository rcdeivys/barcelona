package com.millonarios.MillonariosFC.models.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.millonarios.MillonariosFC.models.Referido;
import com.millonarios.MillonariosFC.models.ReferredData;

/**
 * Created by Amplex on 9/1/2018.
 */

public class ReferredToResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ReferredData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReferredData getData() {
        return data;
    }

    public void setData(ReferredData data) {
        this.data = data;
    }

}