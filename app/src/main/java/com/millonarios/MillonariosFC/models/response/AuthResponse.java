package com.millonarios.MillonariosFC.models.response;

import com.millonarios.MillonariosFC.models.AuthItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Erick on 7/18/17.
 */

public class AuthResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private List<String> error;
    @SerializedName("data")
    @Expose
    private AuthItem data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AuthItem getData() {
        return data;
    }

    public void setData(AuthItem data) {
        this.data = data;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}

