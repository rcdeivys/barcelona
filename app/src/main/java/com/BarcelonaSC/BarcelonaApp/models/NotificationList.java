package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leonardojpr on 11/26/17.
 */

public class NotificationList {

    @SerializedName("notification")
    @Expose
    private List<NotificationItem> notificationItemList;

    public NotificationList() {
    }

    public NotificationList(List<NotificationItem> notificationItemList) {
        this.notificationItemList = notificationItemList;
    }

    public List<NotificationItem> getNotificationItemList() {
        return notificationItemList;
    }

    public void setNotificationItemList(List<NotificationItem> notificationItemList) {
        this.notificationItemList = notificationItemList;
    }
}
