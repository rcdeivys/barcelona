package com.millonarios.MillonariosFC.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Leonardojpr on 11/26/17.
 */

public class NotificationItem {

    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isSuscribe")
    @Expose
    private boolean isSuscribe;

    public NotificationItem(String name, String topic, boolean isSuscribe) {
        this.topic = topic;
        this.name = name;
        this.isSuscribe = isSuscribe;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuscribe() {
        return isSuscribe;
    }

    public void setSuscribe(boolean suscribe) {
        isSuscribe = suscribe;
    }
}
