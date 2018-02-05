package com.millonarios.MillonariosFC.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.millonarios.MillonariosFC.models.WallItem;

import java.util.List;

/**
 * Created by Leonardojpr on 1/23/18.
 */

public class WallResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<WallItem> wallItemList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WallItem> getWallItemList() {
        return wallItemList;
    }

    public void setWallItemList(List<WallItem> wallItemList) {
        this.wallItemList = wallItemList;
    }
}
