package com.millonarios.MillonariosFC.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.millonarios.MillonariosFC.models.SendChooseVoteData;

/**
 * Created by Carlos on 15/01/2018.
 */

public class SendChooseVoteResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private SendChooseVoteData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SendChooseVoteData getData() {
        return data;
    }

    public void setData(SendChooseVoteData data) {
        this.data = data;
    }
}
