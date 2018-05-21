package com.losingtimeapps.whitebrand.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 15/01/2018.
 */

public class SendChooseVoteData {

    @SerializedName("puedevervotos")
    @Expose
    private Integer puedevervotos;

    public Integer getPuedevervotos() {
        return puedevervotos;
    }

    public void setPuedevervotos(Integer puedevervotos) {
        this.puedevervotos = puedevervotos;
    }
}
