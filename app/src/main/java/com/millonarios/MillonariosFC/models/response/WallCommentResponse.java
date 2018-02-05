package com.millonarios.MillonariosFC.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.millonarios.MillonariosFC.models.WallCommentItem;

import java.util.List;

/**
 * Created by Leonardojpr on 1/23/18.
 */

public class WallCommentResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<WallCommentItem> wallCommentItemList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WallCommentItem> getWallCommentItemList() {
        return wallCommentItemList;
    }

    public void setData(List<WallCommentItem> setWallCommentItemList) {
        this.wallCommentItemList = wallCommentItemList;
    }
}
