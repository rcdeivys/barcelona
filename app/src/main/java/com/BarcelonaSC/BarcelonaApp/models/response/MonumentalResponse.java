package com.BarcelonaSC.BarcelonaApp.models.response;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 10/20/17.
 */

public class MonumentalResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private MonumentalItem data;
    @SerializedName("error")
    @Expose
    private List<String> error = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MonumentalItem getData() {
        return data;
    }

    public void setData(MonumentalItem data) {
        this.data = data;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}